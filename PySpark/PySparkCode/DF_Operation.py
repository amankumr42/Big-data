from pyspark.sql import SparkSession
from pyspark.sql.types import StructField, StructType, StringType, LongType
from pyspark.sql.functions import col, column
from pyspark.sql import Row

# Create Spark Session
spark = SparkSession.builder.master("local").appName("Data_Frame_Operation").getOrCreate()

# Create Data Frame
df = spark.read.format("json").option('encoding', 'UTF-8').load(
    "M:/Spark-Learning/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# Print Schema
df.printSchema()

# Check the schema of the loaded json data
print (spark.read.format("json").load("M:/Spark-Learning/Big-data/PySpark/Data/flight-data/json/2015-summary.json")
       .schema)

# Load data into the data frame using custom defined schema
myManualSchema = StructType(
    [StructField("DEST_COUNTRY_NAME", StringType(), True),
     StructField("ORIGIN_COUNTRY_NAME", StringType(), True),
     StructField("count", LongType(), False, metadata={"hello": "world"})])

# Add manual schema in the data frame
df = spark.read.format("json").option('encoding', 'UTF-8').schema(myManualSchema).\
    load("M:/Spark-Learning/Big-data/PySpark/Data/flight-data/json/2015-summary.json")

# df.printSchema()

print (col("someColumnName"))
print (column("someColumnName"))

# Access Data Frame's Columns
print (df.columns)

# Calling first row from data frame
print (df.first)

# Creating ROW
myRow = Row("hello", None, 1, False)
print (myRow[0])
print (myRow[2])

# DataFrame Transformation (1. Add rows or Col.  2. Remove row or Col 3. Transform row into col (vice-versa)

# Create data frame on fly
myManualSchema = StructType(
    [StructField("DEST_COUNTRY_NAME", StringType(), True),
     StructField("ORIGIN_COUNTRY_NAME", StringType(), True),
     StructField("count", LongType(), False, metadata={"hello": "world"})])

myRow = Row("Hello", None, 1)
myDf = spark.createDataFrame([myRow],myManualSchema)
myDf.show()

# Select and SelectExpr
# Selecting single column
df.select("DEST_COUNTRY_NAME").show(2)

# Selecting multiple column
df.select("DEST_COUNTRY_NAME","ORIGIN_COUNTRY_NAME").show(2)

