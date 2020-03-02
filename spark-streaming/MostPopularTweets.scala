package com.amank.sparkstreaming

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.Level
import Utilities._


object MostPopularTweets {
  def main (args : Array[String]){
    setupTwitter()
    val ssc = new StreamingContext ("local[*]","PopularHashtags",Seconds(1))
    setupLogging()
    val tweets = TwitterUtils.createStream(ssc,None)
    val statuses = tweets.map(status=>status.getText())
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))
    val hashTags = tweetwords.filter(word=>word.startsWith("#"))
    val hashTagsKeyValues = hashTags.map(x=>(x,1))
    //Count them up with the window function over 5 minutes and sliding window one seconds
    val hashTagsCount = hashTagsKeyValues.reduceByKeyAndWindow((x,y) => x + y ,(x,y)=> x - y,
        Seconds(300),Seconds(1))
     // short cut -> (_+_,_-_,Seconds(300),Seconds(1))
     val sortedResults = hashTagsCount.transform(rdd=>rdd.sortBy(x=>x._2,false))
     sortedResults.print
     ssc.checkpoint("M:/checkpoint")
     ssc.start()
     ssc.awaitTermination()
  }
}