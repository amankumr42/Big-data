from pyspark.sql.functions import expr
from pyspark.sql import SparkSession
from pyspark import SparkContext
from pyspark.sql.types import StructField, StructType, StringType, LongType
from pyspark.sql.functions import lit
from pyspark.sql import Row
from pyspark.sql.functions import col

# Create Spark Session
spark = SparkSession.builder.master("local").appName("wordCount").getOrCreate()

# create Data Frame in spark

df = spark.read.format("json").option('encoding', 'UTF-8').load(
    "M:/Spark-Learning/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# Print file schema
df.printSchema()

# Check the Schema structure
df.schema

# Define own schema structure and associate custom attribute with it

myManualSchema = StructType(
    [StructField("DEST_COUNTRY_NAME", StringType(), True), StructField("ORIGIN_COUNTRY_NAME", StringType(), True),
     StructField("count", LongType(), True, metadata={"hello": "world"})])

df = spark.read.format("json").schema(myManualSchema).load(
    "M:/Spark-Learning/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# Construct column for data frame

col("someColumnName")

# Working with Row in spark
myRow = Row("Hello", None, 1, False)

# Accessing the rows in spark
myRow[0]
myRow[2]

# Data Frame Operations

myManualSchema = StructType([StructField("some", StringType(), True),
                             StructField("col", StringType(), True),
                             StructField("names", LongType(), False)])

myRow = Row("Hello", None, 17)

myDf = spark.createDataFrame([myRow], myManualSchema)
myDf.show()

# Select and selectExp

df.select("DEST_COUNTRY_NAME").show(2)

# Selecting multiple column from data frame

df.select("DEST_COUNTRY_NAME", "ORIGIN_COUNTRY_NAME").show()

# Select Exp example

df.selectExpr("*", "(DEST_COUNTRY_NAME = ORIGIN_COUNTRY_NAME) as withinCountry").show()

# Adding columns in spark - 1

df.withColumn("numberOne", lit(1)).show(2)

df.withColumn("withinCoutry", expr("ORIGIN_COUNTRY_NAME == DEST_COUNTRY_NAME")).show(2)
