# schedule-message

## Description
Implemented a RESTful web service that schedules messages to be printed. The web service will accept a message content and delivery time. 
- The web service will return 202 status code (accepted) if the message was successfully scheduled. 
- The web service will print the message content on the console at the delivery time specified. 
- The system will recover messages on restart.

## Prerequisites:
- MySQL Database
- Spring Boot
- Spring Data JPA
- JAVA JDK 8

## Setup
```sh
docker-compose up -d #start MySQL DB
```

## Start the service
  ```
  $ mvn clean package
  $ java -jar target/schedule_message-0.0.1-SNAPSHOT.jar
  
  # Test Case
  $ curl --location --request POST 'http://localhost:8080/api/message' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "Test Test",
    "schedule": "2021-10-11 13:59:00"
}'
  ```
## Stop the service
```sh
 docker-compose down
```



