# load data into spark as data frame
flighData2015 = spark.read.option("inferschema","true").option("header","true").csv("../Data/flight-data/csv/2015-summary.csv")

# count number of records in data frame
flighData2015.count()

# Fetch the number of records from data frame
flighData2015.take(3)

# Explain the partition logic
flighData2015.sort("count").explain()


