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


/**
 * @author ${user.name}
 */
object App {

	def main(args : Array[String]) {

		//Start the Spark context
		val conf = new SparkConf()
				.setAppName("Classifier")
				.setMaster("local")
				val sc = new SparkContext(conf)


							// Logistic
						// Load training data in LIBSVM format.
//						val data = MLUtils.loadLibSVMFile(sc, "model")
//				
//								// Split data into training (60%) and test (40%).
//								val splits = data.randomSplit(Array(0.7, 0.3), seed = 11L)
//								val training = splits(0).cache()
//								val test = splits(1)
//				
//								// Run training algorithm to build the model
//								val model = new LogisticRegressionWithLBFGS()
//								.setNumClasses(2)
//								.run(training)
//				
//								// Compute raw scores on the test set.
//								val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
//								val prediction = model.predict(features)
//								(prediction, label)
//						}
//				
//						// Get evaluation metrics.
//						val metrics = new MulticlassMetrics(predictionAndLabels)
//								val accuracy = metrics.accuracy
//								println(s"Accuracy = $accuracy")
//				
//								// Save and load model
//								model.save(sc, "logistic/tmp/scalaLogisticRegressionWithLBFGSModel")
//								val sameModel = LogisticRegressionModel.load(sc,
//										"logistic/tmp/scalaLogisticRegressionWithLBFGSModel")

				// Load and parse the data file.
				val data = MLUtils.loadLibSVMFile(sc, "/Users/dmehta/Documents/EclipseWorkspace/simple-bird-classifier/trainingData")
				// Split the data into training and test sets (30% held out for testing)
				val splits = data.randomSplit(Array(0.7, 0.3))
						val trainingData = splits(0).cache()
								val testData = splits(1)

				// Train a RandomForest model.
				// Empty categoricalFeaturesInfo indicates all features are continuous.
				val numClasses = 2
				val categoricalFeaturesInfo = Map[Int, Int](0 -> 5, 9 -> 3081)
				val numTrees = 100 // Use more in practice.
				val featureSubsetStrategy = "auto" // Let the algorithm choose.
				val impurity = "gini"
				val maxDepth = 5
				val maxBins = 3081
				

				val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
						numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

				// Evaluate model on test instances and compute test error
				val labelAndPreds = testData.map { point =>
				val prediction = model.predict(point.features)
				(point.label, prediction)
		}
		
				// Get evaluation metrics.
						val metrics = new MulticlassMetrics(labelAndPreds)
						val accuracy = metrics.accuracy
						println(s"Accuracy = $accuracy")
		val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
				println("Test Error = " + testErr)
				//println("Learned classification forest model:\n" + model.toDebugString)

				// Save and load model
				model.save(sc, "randomforest/tmp/myRandomForestClassificationModel")
				val sameModel = RandomForestModel.load(sc, "randomforest/tmp/myRandomForestClassificationModel")
	}

}
