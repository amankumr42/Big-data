package com.amank.spark2

import org.apache.spark._
import org.apache.log4j._

object sortingWordCount {
  def main (args : Array[String]){
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]","sortingWordCount")
    val input = sc.textFile("C:/Users/aman_PC/Downloads/SparkScala/SparkScala/book.txt")
    val words = input.flatMap(x => x.split("\\W+"))
    
    //Normalize the words to small
    val lowerCaseWords = words.map(a=>a.toLowerCase())
    val wordCounts = lowerCaseWords.map(x=>(x,1)).reduceByKey((x,y) => x+y)
    //sorting the words
    val wordCountsSorted = wordCounts.map(x => (x._2,x._1)).sortByKey()
    
    wordCountsSorted.foreach(println)
    
  }
  
}