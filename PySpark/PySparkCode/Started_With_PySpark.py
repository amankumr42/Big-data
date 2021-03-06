
# load data into spark as data frame
flighData2015 = spark.read.option("inferschema","true").option("header","true").csv("/root/Big-data/PySpark/Data/flight-data/csv/2015-summary.csv")

# count number of records in data frame
flighData2015.count()

# Fetch the number of records from data frame
flighData2015.take(3)

# Explain the partition logic
flighData2015.sort("count").explain()

# DataFrame and SQL

# DataFrame into table or view
flighData2015.createOrReplaceTempView("flight_data_2015")

# Spark SQL data  Operations
sqlWay = spark.sql("select DEST_COUNTRY_NAME , COUNT(*) as total_number from flight_data_2015 group by DEST_COUNTRY_NAME")

# Data Frame operations
dataFrameWay = flighData2015.groupBy("DEST_COUNTRY_NAME").count()

# compare the DAG for both the operations and underlying execution plan is same for df and sql
sqlWay.explain()
dataFrameWay.explain()

# Using default funtion of spark Sql and Data frame

from pyspark.sql.functions import max
flighData2015.select(max("count")).take(1)

# Sample spark sql
maxSQl = spark.sql("select DEST_COUNTRY_NAME , sum(count) as destination_total from flight_data_2015 group by DEST_COUNTRY_NAME order by 2 desc")

# Sample Spark DF trasformation
from pyspark.sql.functions import desc
flighData2015.groupBy("DEST_COUNTRY_NAME").sum("count").withColumRenamed("sum(count)","destination_total").sort(desc ("DEST_COUNTRY_NAME")).limit(5).show()

# Structured streaming
staticDataFrame = spark.read.format("csv").option("header","true").option("inferschema","true").load("/root/Big-data/PySpark/Data/retail-data/by-day/*.csv")

staticDataFrame.createOrReplaceTempView("retail_data")
staticSchema =  staticDataFrame.schema

# import window function
from pyspark.sql.functions import window , column , desc , col

staticDataFrame.selectExpr("CustomerId","(UnitPrice * Quantity) as total_cost","InvoiceDate").groupBy(col("CustomerId"), window(col("InvoiceDate"), "1 day")).sum("total_cost").show(5)

# Get the Streaming data frame
streamingDataFrame = spark.readStream.schema(staticschema).option("maxFilePerTrigger","1").format("csv").option("header","true").load("/root/Big-data/PySpark/Data/retail-data/by-day/*.csv")

purchaseByCustomerPerHour = streamingDataFrame.selectExpr("CustomerId","(UnitPrice * Quantity) as total_cost","InvoiceDate").groupBy(col("CustomerId"),window(col("InvoiceDate"), "1 day")).sum("total_cost")

purchaseByCustomerPerHour.writeStream.format("memory").queryName("customer_purchases").outputMode("complete").start()


