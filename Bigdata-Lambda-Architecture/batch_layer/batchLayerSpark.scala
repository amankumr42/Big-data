val sc = new SparkContext(conf)
    
//initialize input RDD
val sourceFile = "file:///M:/boxes/spark-kafka-cassandra-applying-lambda-architecture/vagrant/data/data.txt"
  
val input = sc.textFile(sourceFile)
//Spark Action
input.foreach(println)

	
//case class 
	
case class Activity (timestamp_hour : Long , referrer : String , action : String , prevPage:String , page : String , visitor:String , product :String
,inputProps : Map [String , String] = Map())
	
/* RDD operations */
	
val inputRdd = input.flatMap (line => val record = line.split(",")
val MS_IN_HOUR = 1000*60*60
if (record.length == 7)
Activity(record(0).toLong /MS_IN_HOUR * MS_IN_HOUR ,record(1),record(2),record(3),record(4),record(5),record(6))	
else
None
)

//Aggregration on column product
	
	val keyedByProduct = inputRDD.keyBy(a=>(a.product , a.timestamp_hour)).cache()
	val visitorByProduct = keyedByProduct.mapValues)(a => a.visitor).distinct().countByKey()
	
val activityByProduct = keyedByProduct.mapValues{a=> a.action match{
	case "purchase" => (1,0,0)
	case "add_to_cart" => (0,1,0)
	case "page_view" => (0,0,1)	
}}.reduceByKey ((a,b) => (a._1 + b._1,a._2 + b._2 , a._3 + b._3)) 

visitorByProduct.foreach(println)
activityByProduct.foreach (println)

//using spark sql and data frame to get the desired result
//this is used to implicitly convert RDD to a DataFrame to use spark sql 

val sqlContext = new org.apache.spark.sql.SQLContext(sc)
val MS_IN_HOUR = 1000*60*60 
import sqlContext.implicits._
val inputRDD = input.map(lines => lines.split("\\t"))
case class Activity (timestamp_hour : Long , referrer : String , action : String , prevPage:String , page : String , visitor:String , product :String
,inputProps : Map [String , String] = Map())
val rddToDf = inputRDD.map( p=> Activity ( p(0), p(1),p(2),p(3),p(4),p(5))).toDF()
rddToDf.registerTempTable("click_tbl")

val testSql = sqlContext.sql("select count(*) from click_tbl")
val testDistVal = sqlContext.sql(" select distinct product from click_tbl")


//Testing on IRIS CSV

val input = sc.textFile("file:///M:/boxes/spark-kafka-cassandra-applying-lambda-architecture/vagrant/data/iris.csv")

//Remove the header

val header = input.first()
val withoutHeader = input.filter(row => row!= header)

val iris = input.map(records => ColumnName(records(0).toFloat,records(1).toFloat,records(2).toFloat,records(3).toFloat,records(4))).toDF()
val iris = withoutHeader.map(lines => lines.split(",")).map(l=> ColumnName(l(0).toFloat,l(1).toFloat,l(2).toFloat,l(3).toFloat,l(4).replaceAll("^\"|\"$",""))).toDF()

//Register RDD as the table

iris.registerTempTable("iris")

val iris_df = sqlContext.sql("select * from iris")

val countBySpecies = sqlContext.sql("select species , count(*) from iris group by 1 ")

val sepalLengthCond = sqlContext.sql("select species , count(*) from iris where sepal_length >= 4.0 and sepal_width >=2.0 and Petal_length <=1.0  group by 1")

