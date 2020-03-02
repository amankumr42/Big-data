val sqlContext = new org.apache.spark.sql.SQLContext(sc) 

import sqlContext.implicits._

case class smscColname(procdate :String , originatingoperator :String ,originatingcircle :String ,vmsc :String ,
vmsc_lookup :String , terminatingoperator :String,terminatingcircle :String ,senderid :String ,msgtype :String ,
mapid :String , procmonth :String ,time :String , milisec :String , imsi :String , imsi_lookup :String ,actualbpartycircle
: String , actualbpartyoperator :String , smc :String ,message_len :String , sucess_timestamp :String ,file_name :String ,
calledbparty :String )

val smscDf = sc.textFile("/user/amank/newData/finalOutput_2018-02-02.csv").map(a=>a.split(",")).map(p=>smscColname(p(0),p(1)
,p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13),p(14),p(15) ,p(16),p(17),p(18),p(19),p(20),p(21)))

object EtlFunc{ 
	def etlFun(rdd:RDD[String]): RDD[String] = {
		var splitRow = rdd.map(a=>a.split(",")).
	} 
}

val rawData = sc.textFile("/user/amank/newData/finalOutput_2018-02-02.csv").map(a=>a.split(","))

val rawDataProcDate = rawData.map(a=>a(0))

val restData = rawData.map(p=>(p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13),p(14),p(15) ,p(16),p(17),p(18),p(19),p(20),p(21)))

//rawDataProcDate.union(restData).take(2)

//create a dataframe

val sqlContext = new  org.apache.spark.sql.SQLContext(sc)

case class smscColname(procdate :String , originatingoperator :String ,originatingcircle :String ,vmsc :String ,
vmsc_lookup :String , terminatingoperator :String,terminatingcircle :String ,senderid :String ,msgtype :String ,
mapid :String , procmonth :String ,time :String , milisec :String , imsi :String , imsi_lookup :String ,actualbpartycircle
: String , actualbpartyoperator :String , smc :String ,message_len :String , sucess_timestamp :String ,file_name :String ,
calledbparty :String )


val rawData = sc.textFile("/user/amank/newData/finalOutput_2018-02-02.csv").map(a=>a.split(",")).map(p=>smscColname(p(0),p(1)
,p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13),p(14),p(15) ,p(16),p(17),p(18),p(19),p(20),p(21)))

val dataFrame = rawData.toDF()


