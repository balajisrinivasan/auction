How to run the application:

JDK, Maven is required execute the application. Application can be run in one of the following ways:

 	Application can be run by importing the maven project into IDE and running the main task `AuctionApplication`.java
 	Maven cli comman "mvn spring-boot:run"

Assumptions and constraints:

There are many ways to build an auction system and the correction approach depends on variety of parameters like functional use case, throughput/latency etc. 

Given the time constraint and my from understanding of the problem statement, I've designed a simple auction system to process batch file of auction events. Some of the assumptions I made to build this:

    Ingest rate is number of messages per second. I achieved this by processing specified number of messages and then putting the thread to sleep for a second. Thread.sleep doesn't guarantee that thread will be back to running in exactly one second but that's the solution I could think of. If ingest rate was to create a buffer, I would have used BlockingQueue between the ingestor and the processor.

    Since this appliction only process auction events file, I used a map to track the auction end time for all auctions. Otherwise, I'd have some in-memory/persistant datastore to track auction expiry and a timer task/scheduler to look for auction expiry and trigger auction expired event.	