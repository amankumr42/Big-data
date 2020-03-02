//load HiveContext
val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)

//initialize the sql
val hiveRes = hiveContext.sql("select procdate , senderid , originatingoperator from smsc.smsc_data_init")

//The result from sql query is DataFrame and RDD operation can easily applied on result
hiveRes.map(p=>"procdate:  " + p(0)).collect.foreach(println)

//access the value of the column by the column name
hiveRes.map(p=>"senderid:  "+p.getAs[String]("senderid")).collect().foreach(println)

//access the value of the multiple columns
hiveRes.map(p=>p.getValuesMap[Any](List("procdate","senderid"))).collect().foreach(println)

