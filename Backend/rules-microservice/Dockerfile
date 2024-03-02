FROM openjdk:17
EXPOSE 9002
ADD target/rules-microservice.jar rules-microservice

ENTRYPOINT ["java","-jar","rules-microservice.jar"]
