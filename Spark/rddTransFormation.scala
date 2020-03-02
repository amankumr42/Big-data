//MAP function
val rddSplitFile = rdd1.map(line=>line.split(","))

//FLATMAP
scala> sc.parallelize(List(1,2,3)).flatMap(x=>List(x,x,x)).collect
res15: Array[Int] = Array(1, 1, 1, 2, 2, 2, 3, 3, 3)

//Filter similar like 'where' clause in sql
val x = sc.parallelize(1 to 10, 2)
val y = x.filter(e=>e%2==0)
y.collect

//MAPPARTITIONS
/*
	used as performance optimization (if you have the cluster increase the processig speed of spark)
*/
val parallel = sc.parallelize(1 to 9, 3)
parallel.mapPartitions( x => List(x.next).iterator).collect
val parallel = sc.parallelize(1 to 9)
parallel.mapPartitions( x => List(x.next).iterator).collect

//UNION

val sqlResUnion = new org.apache.spark.sql.hive.HiveContext(sc)
val getTble1 = sqlResUnion.sql("select procdate , senderid from smsc.smsc_data_par limit 1000")
val getTble2 = sqlResUnion.sql("select imsi_lookup ,actualbpartyoperator ,actualbpartycircle from smsc.smsc_data_init limit 1000")
val unionTble = getTble1.union(getTble2).collect

/*Error union is not member of dataframe so only RDD is going to be used*/

val loadData1 = sc.textFile("/user/amank/finalOutput_20180201.csv")
val rddData1 = loadData1.map(line=>line.split(","))

val loadData2 = sc.textFile("/user/amank/finalOutput_20180202.csv")
val rddData2  = loadData2.map(a=>a.split(","))
val unionRdd = rddData1.union(rddData2)

//INTERSECTION
val parallel = sc.parallelize(1 to 9)
val par2 = sc.parallelize(5 to 15)
parallel.intersection(par2).collect

//Distinct 
parallel.intersection(par2).distinct.collect



 