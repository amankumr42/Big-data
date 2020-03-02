# Big Data data pipeline

# Objective
The main purpose of this project is to aggregrate the logs from different server to  hadoop cluster , perform ETL operartions and
use this as for real time log analysis ,OLTP ,OLAP and report generations.

# Stacks used 

1.Flume agent to transfer log and receiving log.

2.Kafka message broker , kafka consumer leverage to  use the real time streaming data useb by various hadoop components.

3.Streamsets data collector allows us to build data pipeline , perform ETL and load data in hadoop cluster , DBMS ,ELK etc.




