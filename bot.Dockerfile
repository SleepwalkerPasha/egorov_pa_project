FROM amazoncorretto:17.0.1-alpine
EXPOSE 8080
LABEL authors="Pavlik"
ARG JAR_FILE=./bot/target/*SNAPSHOT.jar
ADD ${JAR_FILE} bot.jar
ENTRYPOINT ["java", "-jar", "/bot.jar"]