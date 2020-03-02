package com.amank.spark

import org.apache.spark._
import org.apache.log4j._

object wordCount {

  def main (args: Array[String]){
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext ("local[*]","WordCount")
    
    val lines = sc.textFile("C:/Users/aman_PC/Downloads/SparkScala/SparkScala/book.txt")
    //lines.foreach(println)
    val words = lines.flatMap(x => x.split(" "))
    val countByWord = words.countByValue()
    
    countByWord.foreach(println)
    
  }
}