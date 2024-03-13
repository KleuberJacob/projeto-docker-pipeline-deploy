FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-basica.jar
ENTRYPOINT ["java", "-jar", "/api-basica.jar"]