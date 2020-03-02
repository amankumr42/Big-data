package com.amank.sparkstreaming

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.Level
import Utilities._

object SaveTweets {
  def main(args : Array[String]){
    setupTwitter()
    
    val ssc = new StreamingContext("local[*]","SaveTweets",Seconds(1))
    setupLogging()
    //create RDD of stream's
    val tweets = TwitterUtils.createStream(ssc,None)
    //Extract text from each tweet
    val statuses = tweets.map(status => status.getText())
    //Keep count the number of tweets want to insert
    var totalTweets:Long = 0
    
    statuses.foreachRDD((rdd,time)=>{
      if (rdd.count() >0){
        val repartitionRDD = rdd.repartition(1).cache()
        //AND print out a directory with the results
        repartitionRDD.saveAsTextFile("Tweets_"+time.milliseconds.toString)
        //Stop once we collected 100 tweets
        totalTweets += repartitionRDD.count()
        println("Tweet count :"+ totalTweets)
        if (totalTweets > 1000){
          System.exit(0)
        }
      }
    })
    ssc.checkpoint("M:/checkpoint")
    ssc.start()
    ssc.awaitTermination()
  }
}