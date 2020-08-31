from pyspark import SparkContext
from pyspark.streaming import StreamingContext

# Create a local streamingContext with two working thread and batch interval of 1 seconds

sc = SparkContext("local[2]","Reading from file")
ssc = StreamingContext(sc,1)

lines = ssc.textFileStream("M:/data/gen_logs/logs")

# Split each lines into DStream

words = lines.flatMap(lambda line : line.split(","))

# Count each word in each batch

pairs = words.map(lambda word : (word,1))

wordCounts = pairs.reduceByKey(lambda x,y: x+ y )

# Print the first ten elements of each RDD generatad into this DStram to the console

wordCounts.pprint()

ssc.start() # Start the Computation
ssc.awaitTermination() # Wait for the Computation to terminate

