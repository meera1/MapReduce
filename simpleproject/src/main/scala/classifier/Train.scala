package classifier;

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.apache.spark.storage.StorageLevel._

import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.feature.PCA

import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}


/**
 * @author ${user.name}
 */
object Train {

	def fit(sc : SparkContext, args: Array[String]) {

		// Load and parse the data file.
		val data = MLUtils.loadLibSVMFile(sc, args(0)+"/trainingData")
				//val pca = new PCA(10).fit(data.map(_.features))
				// Split the data into training and test sets (30% held out for testing)
				val splits = data.randomSplit(Array(0.7, 0.3), seed = 11L)
				val trainingSplit = splits(0).cache()
				val testSplit = splits(1).cache()
				val testSplitCount = testSplit.count

				// Train a RandomForest model.
				// Empty categoricalFeaturesInfo indicates all features are continuous.
				val numClasses = 2
				val categoricalFeaturesInfo = Map[Int, Int](0 ->5, 9 -> 3081)
				val numTrees = 10 // Use more in practice.
				val featureSubsetStrategy = "auto" // Let the algorithm choose.
				val impurity = "gini"
				val maxDepth = 7
				val maxBins = 3081


				val model = RandomForest.trainClassifier(trainingSplit, numClasses, categoricalFeaturesInfo,
						numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

				// Evaluate model on test instances and compute test error
				val labelAndPreds = testSplit.map { point =>
				val prediction = model.predict(point.features)
				(point.label, prediction)
		}


		val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testSplitCount
				println("Test Error = " + testErr)

				// Save and load model
				model.save(sc, args(2))


	}

}
