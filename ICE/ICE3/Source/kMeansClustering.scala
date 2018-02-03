import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

/**
  * Created by Manikanta on 1/31/2017.
  */
object kMeansClustering {

  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir","C:\\Users\\Manikanta\\Documents\\UMKC Subjects\\PB\\hadoopforspark");
    System.setProperty("hadoop.home.dir","C:\\big_data");
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    // Turn off Info Logger for Consolexxx
    Logger.getLogger("org").setLevel(Level.OFF);
    Logger.getLogger("akka").setLevel(Level.OFF);
    // Load and parse the data
    val data = sc.textFile("kMeans/data/3d.txt")
    val parsedData = data.map(s => Vectors.dense(s.split(',').slice(0,4).map(_.toDouble))).cache()


    //Look at how training data is!
    parsedData.foreach(f=>println(f))

    // Cluster the data into two classes using KMeans
    val numClusters = 4
    val numIterations = 20
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsedData)
    println("Within Set Sum of Squared Errors = " + WSSSE)

    //Look at how the clusters are in training data by making predictions
    println("Clustering on training data: ")
    clusters.predict(parsedData).zip(parsedData).foreach(f=>println(f._2,f._1))

    // Save and load model
    clusters.save(sc, "kMeans/data1/KMeansModel")
    val sameModel = KMeansModel.load(sc, "kMeans/data1/KMeansModel")


  }


}
