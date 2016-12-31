package classifier

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.{Vector, Vectors}

object Prediction {
  
  def predict(sc: SparkContext, args: Array[String]): Unit = {
          
        val sameModel = RandomForestModel.load(sc, args(2))
				val testData = sc.textFile(args(0)+"/testingData")
				              .map(_.split(","))
				              .map(fields => (fields(0),fields(1)))
				              .map{ tuple => 
				                val features = tuple._2.split(" ")            
				                val points = features.map(_.split(":")).map(fields => (fields(0).toInt,fields(1).toDouble)).toList            
				                val spareseVector = Vectors.sparse(23, points)
				                (tuple._1, spareseVector)
		                      }
				
				val labelAndPreds2 = testData.map { point =>
				            val prediction = sameModel.predict(point._2)
				            (point._1, prediction)
		      }.saveAsTextFile(args(1))
  }
}