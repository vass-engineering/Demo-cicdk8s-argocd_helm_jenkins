FROM openjdk:11-slim
ARG JAR_FILE=ContinuousIntegrationDelivery/argo_helm_Jenkins/tms-spring-boot-template-demo/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
