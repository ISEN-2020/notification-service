# Notification service via email
## Description
Notification-service is a **Java** microservice developed with the **SpringBoot** framework.  
Its purpose is to send notifications by **mail** to users who have borrowed one or more books for a too long period.  

## How it works
This list of books is provided by the ***Lending Management*** microservice via an http request *POST*. This micro-service is requested every day.
In response to this request the microservice receives data formatted in *JSON*.
The *JSON* data is extracted and transformed to form the mail.
The mail is then sent via the *smtp* server of *google*.  

## Environment
Maven for dependency management and build  
JDK version: 1.8
Spring-boot: 2.1.8
Sonarcloud :
	- JUnit version 5: unit test management
	- JaCoCo version: 0.8.7 for test code coverage

## Build
The build of the source code is done with *Maven* via the command `mvn package spring-boot:repackage`.
Added 10/09/2021, Sonarcloud configuration: 
	- JUnits plug-in
	- JaCoCo plug-in

## Usage
To launch the microservice locally on port 8080, run the command `java -jar microservice-notification-0.0.1.jar`

## Adding an end point
The addition of an *Endpoint* in the microservice is done in the class `NotificationController.java`.
