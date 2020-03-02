//Starting Point : SQLContext
val sqlContext = new org.apache.spark.sql.SQLContext(sc) 

//this is used to implicity convert an RDD to a DataFrame
import sqlContext.implicits._

case class smscColname(procdate :String , originatingoperator :String ,originatingcircle :String ,vmsc :String ,
vmsc_lookup :String , terminatingoperator :String,terminatingcircle :String ,senderid :String ,msgtype :String ,
mapid :String , procmonth :String ,time :String , milisec :String , imsi :String , imsi_lookup :String ,actualbpartycircle
: String , actualbpartyoperator :String , smc :String ,message_len :String , sucess_timestamp :String ,file_name :String ,
calledbparty :String )

//create and RDD and register as a table
val smscDf = sc.textFile("/user/amank/newData/finalOutput_2018-02-02.csv").map(a=>a.split(",")).map(p=>smscColname(p(0),p(1)
,p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13),p(14),p(15) ,p(16),p(17),p(18),p(19),p(20),p(21)))

val smsc= smscDf.toDF()
smsc.registerTempTable("smsc")

//SQL statement can be run by using the sql methods provided by sqlContext
val sqlRes = sqlContext.sql("select procdate , msgtype , count(*) as count from smsc group by procdate ,msgtype")
sqlRes.show()

//The results of SQL query are DataFrame and support all the normal RDD operations
//The columns of the row can be accessed through the field index

sqlRes.map(t=> "date"+"  "+ t(0)).collect().foreach(println)

//accessing the columns by field name
sqlRes.map(t=> "msgtype: " + t.getAs[String]("msgtype")).collect().foreach(println)

//row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
sqlRes.map(t=>t.getValuesMap[Any](List("procdate","count"))).collect.foreach(println)




