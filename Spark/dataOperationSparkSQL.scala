val sqlContext = new org.apache.spark.sql.SQLContext(sc) 

import sqlContext.implicits._

case class smscColname(procdate :String , originatingoperator :String ,originatingcircle :String ,vmsc :String ,
vmsc_lookup :String , terminatingoperator :String,terminatingcircle :String ,senderid :String ,msgtype :String ,
mapid :String , procmonth :String ,time :String , milisec :String , imsi :String , imsi_lookup :String ,actualbpartycircle
: String , actualbpartyoperator :String , smc :String ,message_len :String , sucess_timestamp :String ,file_name :String ,
calledbparty :String )

val smscDf = sc.textFile("/user/amank/newData/finalOutput_2018-02-02.csv").map(a=>a.split(",")).map(p=>smscColname(p(0),p(1)
,p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13),p(14),p(15) ,p(16),p(17),p(18),p(19),p(20),p(21)))

val smsc= smscDf.toDF()

//DATAFRAME Operations

//show the content of the DATAFRAME

smsc.show()

smsc.select("senderid").show()//show thw result of senderid

//select multiple columns
smsc.select(smsc("procdate"),smsc("originatingcircle")).show()

//select filter
smsc.filter(smsc("senderid")==="DM-Xiaomi").show()

//group by

smsc.select(smsc("procdate"),smsc("senderid")).groupBy(smsc("senderid"),smsc("procdate")).count().show()

//Running the SQL Queries programmatically

val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)
val res = hiveContext.sql("select count(*) , procdate , senderid from smsc.smsc_data_par group by procdate , senderid");

