# TransactionAnalyser
Utility to analyse transaction data from a csv file 

Utility reads CSV files exctracts out transaction and calculates the average amount spent and total number of transactions.

Assumption - 
CSV file has transaction valid data in following format -
ID, Date, Amount, Merchant, Type, Related Transaction

Constraint -
To keep solution simple for now, valid data (valid for calculating stats) is kept in memory.

Prequisites -
Java 8, Gradle 4.4

Steps to Run the utility - 
1. Create a Jar with gardle command - 
```
  gradle build
 ```
Example -
```
E:\workDir\TransactionAnalyser_ClonedDir>gradle build
Starting a Gradle Daemon (subsequent builds will be faster)

BUILD SUCCESSFUL in 49s
6 actionable tasks: 6 executed
```

2. Go to the folder where JAR has been created.Generally its <cloned directory>\build\libs.
  Find the fat jar with all libraries included with name TransactionAnalyser-1.0-all. And execute it.
  
  ```
  Example - 
E:\workDir\TransactionAnalyser_ClonedDir\build\libs>java -jar TransactionAnalyser-1.0-all.jar
Enter merchant name:
Kwik-E-Mart
Enter date from-
20/08/2018 12:00:00
Enter date to-
20/08/2018 13:00:00
Enter csv file location (absolute Path)
E:\workDir\TransactionAnalyser_ClonedDir\build\libs\input.csv
OutputStats{total transaction count=1, avg amount spent=59.99}

```


