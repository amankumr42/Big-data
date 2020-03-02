package com.amank.spark2

import org.apache.spark._
import org.apache.log4j._

object wordCountRegX {
     Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]","WordCount")
    val input = sc.textFile("C:/Users/aman_PC/Downloads/SparkScala/SparkScala/book.txt")
    val words = input.flatMap(x => x.split("\\W+"))
    
    //Normalize the words to small
    val lowerCaseWords = words.map(a=>a.toLowerCase())
    val wordCounts = lowerCaseWords.countByValue()
    
    //print the result
    wordCounts.foreach(println)
    
}