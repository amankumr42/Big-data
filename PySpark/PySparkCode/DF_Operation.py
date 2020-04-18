# create Data Frame in spark

df = spark.read.format("json").load("/root/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# Print file schema
df.printSchema()

# Check the Schema structure
spark.read.format("json").load("/root/Big-data/PySpark/Data/flight-data/json/2015-summary.json").schema

# Define own schame structure and associate custom attribute with it

from pyspark.sql.types import StructField, StructType, StringType, LongType
myManualSchema = StructType([StructField("DEST_COUNTRY_NAME",StringType(),True), StructField("ORIGIN_COUNTRY_NAME", StringType(),True),StructField("count",LongType(),False,metadata={"hello":"world"})])

df = spark.read.format("json").schema(myManualSchema).load("/root/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# Contruct column for data frane

from pyspark.sql.functions import col, columns
col("someColumnName")

# or

column("someColumnName")

# Explictlt call any column
col.df("count")

# Working with Row in spark

from pyspark.sql import Row
myRow = Row("Hello",None,1,False)

# Accessing the rows in spark
myRow[0] 
myRow[2]
