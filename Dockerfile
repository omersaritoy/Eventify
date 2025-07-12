FROM openjdk:21
ARG JAR_FILE=target/Eventify-0.0.1-SNAPSHOT.jar
COPY ./target/Eventify-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]