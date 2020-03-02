//spark-shell --packages org.apache.kudu:kudu-spark_2.10:1.1.0


import org.apache.kudu.spark.kudu._
import org.apache.kudu.client._
import collection.JavaConverters._
import org.apache.spark.sql._
import org.apache.spark.sql.types._

val kuduMaster = "rmlin-hadoopm.infra.rmlconnect.net:7051"
val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)
val hiveRes = hiveContext.sql("select procdate , senderid , mapid, originatingoperator from smsc.smsc_data_init limit 10000")
val kuduContext = new KuduContext("localhost:7051")

//check if the table exists and delete

val kuduTableName="smsc_data_arch"


if (kuduContext.tableExists(kuduTableName)) {
 kuduContext.deleteTable(kuduTableName)
}


val kuduTableSchema =  StructType(
	StructField("procdate",StringType,false)::
	StructField("senderid",StringType,false)::
	StructField("mapid",StringType,false)::
	StructField("originatingoperator",StringType,false)::Nil
)

val kuduPrimaryKey=Seq("mapid")

val kuduTableOptions =new CreateTableOptions()

val kuduOptions: Map[String, String] = Map(
 "kudu.table"  -> kuduTableName,
 "kudu.master" -> kuduMaster)

kuduTableOptions.
	setNumReplicas(1).addHashPartitions(List("mapid").asJava, 3)

/*
kuduTableOptions.
	setRangePartitionColumns(List("mapid").asJava).setNumReplicas(1)
*/

kuduContext.createTable(
	kuduTableName,kuduTableSchema,kuduPrimaryKey,kuduTableOptions
)	

//DML – Insert, Insert-Ignore, Upsert, Update, Delete with KuduContext

//Read a table from the kudu

kuduContext.insertRows(hiveRes,"smsc_data_arch")

//Delete
val delDf = sqlContext.sql("select mapid from smsc.smsc_data_par where senderid='DM-SBGMBS' limit 10000")


val kuduTable=sqlContext.read.options(Map("kudu.master"->"rmlin-hadoopm.infra.rmlconnect.net:7051",
"kudu.table"->"smsc_data_arch")).kudu 

//Query using Spark API
val filteredDf=hiveRes.select("senderid").filter(hiveRes("senderid")==="DM-SBIUPI")
kuduContext.deleteRows(filteredDf,"smsc_data_arch")
//create a new schema table from a database schema
//NB:No rows from the dataframe are inserted into the table


//Delete
kuduContext.deleteRows(filteredDf,"smsc_data_arch")
kuduContext.insertRows(hiveRes,kuduTableName)
sqlContext.read.options(kuduOptions).kudu.show

hiveRes.registerTempTable("hiveRes")
val deleteDf=sqlContext.sql("select senderid  from smsc.smsc_data_par where senderid='DM-014530' limit 10000")


// Delete the rows from our Kudu table
kuduContext.deleteRows(filteredDf, kuduTableName)

//Read data from kudu table
sqlContext.read.options(kuduOptions).kudu.show