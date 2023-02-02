FROM openjdk:17-alpine
WORKDIR /usr/src/main
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} person-restapi.jar
ENTRYPOINT ["java","-jar","person-restapi.jar"]
EXPOSE 9090