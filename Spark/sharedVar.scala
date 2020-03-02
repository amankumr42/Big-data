//load data
val loadData1 = sc.textFile("/user/amank/finalOutput_20180207.csv")
val splitData = loadData1.map(a=>a.split(","))
/*
	 Broadcast variables allow Spark developers to keep a secured read-only variable cached 
	on different nodes, other than merely shipping a copy of it with the needed tasks
*/


//Use broadcast var
val broadcastVar = sc.broadcast(splitData)
broadcastVar.value

val broadcastTest = sc. broadcast(Array(1,2,3))
broadcastTest.value

//Accumulators
val accum = sc.accumulator(0, "My Accumulator")
sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum += x)
accum.value




