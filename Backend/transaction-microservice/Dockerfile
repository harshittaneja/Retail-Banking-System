FROM openjdk:17
EXPOSE 9003
ADD target/transaction-microservice.jar  transaction-microservice.jar

ENTRYPOINT ["java","-jar","transaction-microservice.jar"]
