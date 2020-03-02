/* spark-shell*/
//load the file into the spark
val inFile = sc.textFile("/testSms.csv")

inFile.count()// count the number of lines from the record 
inFile.take(4)//to return the 4 rows from the file
inFile.first()//display the first row from the file

//map() to iterative over the elements of the RDD and covert into the usable format

val newData = inFile.map(line=>line.split(","))//RDD are formatted in comma seperated

/* Spark session*/
/* A SparkSession object represents the connection to a Spark cluster (local or remote) and
provides the entry point to interact with Spark*/


//Load data from hive store
val sqlContext = new 	

val a = sqlContext.sql("select count(procdate) from smsc.smsc_data_par");
val convDfToString = a.map(line=>line.toString) 

//Passing the function

object myFunction{
	def func1 (s: String) : String = {
		s=>s.toLowerCase
	}
}
myRdd.map(myFunction.func1	)