package classifier

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object App {
  def main(args: Array[String]): Unit = {
    
    val conf = new SparkConf()
				.setAppName("Classifier")
				//.setMaster("local")
				val sc = new SparkContext(conf)
    
    Train.fit(sc, args)
    Prediction.predict(sc, args)
    
  }
}