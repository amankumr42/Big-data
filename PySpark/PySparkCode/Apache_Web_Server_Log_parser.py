from pyspark.sql import SparkSession
import re

# Create Spark Session
spark = SparkSession.builder.master("local").appName("HttpsLogParser").getOrCreate()

def getData(date):
    strctureDate = str(date)[2:-1]
    splitTheDate = strctureDate.split(":")
    getDate = splitTheDate[0]
    getTheDateTime = getDate + " " + splitTheDate[1] + ":" + splitTheDate[2] +  ":" +splitTheDate[3]
    return getTheDateTime

def getTheRequestType(data):
    splitResponseData = data.replace('"',"").replace(']',"")

    splitResponseData = data.split(" ")
    requestType = splitResponseData[1]
    responseEndPoint = splitResponseData[2]

    return requestType

def getTheRequestEndPoint(data):
    splitResponseDate = data.replace('"',"").replace(']',"")
    splitResponseData = data.split(" ")
    endPoint = splitResponseData[2]
    return endPoint

def getTheRequestType(data):
    splitResponseDate = data.replace('"',"").replace(']',"")
    splitResponseData = data.split(" ")
    return splitResponseData[3]

def getTheRequestCode(data):
    splitResponseDate = data.replace('"', "").replace(']', "")
    splitResponseData = data.split(" ")
    return splitResponseData[4]

def getTheTagetOS(data):
    machineDetails = re.search(r'\((.*?)\)',data).group(1)
    machineDetails = machineDetails.split(";")
    return machineDetails[0].replace(" ","_") + "_" + machineDetails[1]

def getTheBrowserNameAndVersion(data):
    splitData = data.split(" ")
    numberOfArray = len(splitData)
    browserVersion = splitData[numberOfArray-1].split("/")
    return browserVersion[0] + "_" + browserVersion[1]

# Create Spark RDD and load data
readLogFile = spark.sparkContext.textFile("M:/data/HTTP_LOG/*")

# Count number of lines of text file
countNoOfRows = readLogFile.count()

# Count the number of rows in log file
print (countNoOfRows)

# Split data log file
splitLogFile = readLogFile.map(lambda line : line.split("-"))

# Split the line
seperateLogContent = splitLogFile.map(lambda line : (line[0],getData(line[2]),getTheRequestType(line[3]),
                                                     getTheRequestEndPoint(line[3]),getTheRequestCode(line[3]),
                                                     getTheTagetOS(line[4]),getTheBrowserNameAndVersion(line[4])))

print (seperateLogContent.first())