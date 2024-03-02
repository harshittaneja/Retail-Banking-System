FROM openjdk:17
EXPOSE 8761
ADD target/service-discovery.jar  service-discovery.jar

ENTRYPOINT ["java","-jar","service-discovery.jar"]
