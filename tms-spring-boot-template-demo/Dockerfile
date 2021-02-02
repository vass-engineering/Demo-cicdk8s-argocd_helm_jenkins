FROM openjdk:11-slim
ARG JAR_FILE=tms-spring-boot-template-demo/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
