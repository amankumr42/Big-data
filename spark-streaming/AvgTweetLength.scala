package com.amank.sparkstreaming

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.Level
import Utilities._
import java.util.concurrent._
import java.util.concurrent.atomic._

object AvgTweetLength {
  def main (args : Array[String]){
  setupTwitter()
  val ssc = new StreamingContext("local[*]","AvgTweetLength",Seconds(1))
  setupLogging()
  val tweets = TwitterUtils.createStream(ssc,None)
  
  val status = tweets.map (status=> status.getText())
  //map this to tweet character length
  val length = status.map(status=> status.length())
 //In order to calculate the results from these two D stream we need to implement the thread safe
  val totalTweets = new AtomicLong(0)
  val totalChars = new AtomicLong(0)
  
  length.foreachRDD((rdd,time) =>{
    
    var count = rdd.count()
    if (count > 0)
      totalTweets.getAndAdd(count)
      totalChars.getAndAdd(rdd.reduce((x,y) => x +y ))
      
      println("Total Tweets :" + totalTweets.get() + "Total Character :" + totalChars.get() + "Average: "+
          totalChars.get() /totalTweets.get())
  }    
  )
    ssc.checkpoint("M:/checkpoint")
    ssc.start()
    ssc.awaitTermination()
}
  }