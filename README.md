# Spring Boot with PostgreSQL and Docker Compose Example

## STEPS FOR THIS SPRING BOOT APP
- Define dependencies in build.gradle
- Create a Spring Boot Main @SpringBootApplication
- Create components @Entity / @RestController / @Repository
- Create application.properties
- Build 
- Check app using curls 

## BUILD the application 
./gradlew build   

## BUILD AND UP Docker Compose 
docker-compose up --build   
docker-compose down <- down docker compose     

## CURLS 

### PUT /user/save 
curl -X PUT \
  http://localhost:8081/balance/change/ad7d84ab-0781-4c04-a354-9c185fd1e33f \
  -H 'accept: application/json' \
  -H 'content-type: application/json' \

  -d '{
"amount": 30000,
"transactionType": "REPLENISH",
"type":"type"
"postBalance" :16000,
"description" :"dec"
}'


### GET /execute
curl -s -X GET \
  http://localhost:8081/execute
  
  
## Complete explanation
english: https://experto.dev/en/spring-boot-with-postgresql-and-docker-compose/   
spanish: https://experto.dev/spring-boot-postgresql-docker-compose/   
