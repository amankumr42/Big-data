package com.amank.spark2

import org.apache.spark._
import org.apache.log4j._
object sortCustomerPurchase {
    def parseLine(line:String) ={
    //Split by comma
    val fields = line.split(",")
    //Extract the age and numberOfFriend fields , and convert into integer
    val age = fields(2).toInt
   // val name = fields(1)
    val numFriends = fields(3).toInt
    //create tuple that is our return statement
    (age,numFriends)
  } 
   def main (args : Array[String]){
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]","totalMoneySpentByCustomer")
    val input = sc.textFile("C:/Users/aman_PC/Downloads/SparkScala/SparkScala/customer-orders.csv")
    val parsedLine = input.map(parseLine)
    //parsedLine.foreach(println)
    val findSpentAmount = parsedLine.reduceByKey((x,y)=> x + y)
    val results = findSpentAmount.collect()
    val resultsSorted = findSpentAmount.map(x => (x._2,x._1) ).sortByKey()
    for (result <-resultsSorted){
      val customId = result._2
      val spentAmount = result._1
      println(s"$customId:$spentAmount ")
    }
    
    //results.foreach(println)
    
    //sort the data (know who is the most potential customer and cheap customer)
    
  }
} 	