
FROM openjdk:8

VOLUME /tmp

copy ./target/request-demo.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","/app.jar"]
