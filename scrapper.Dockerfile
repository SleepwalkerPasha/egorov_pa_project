FROM amazoncorretto:17.0.1-alpine
EXPOSE 8081
LABEL authors="Pavlik"
ARG JAR_FILE=./scrapper/target/*SNAPSHOT.jar
ADD ${JAR_FILE} scrapper.jar
ENTRYPOINT ["java", "-jar", "/scrapper.jar"]