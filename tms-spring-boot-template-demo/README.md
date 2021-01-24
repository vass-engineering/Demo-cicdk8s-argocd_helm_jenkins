# TMS Sring Boot Template

## How to build

```
mvn clean package
docker build -t tms/spring-boot-template .
./mvnw spring-boot:build-image
```

## How to test in local

```
docker run -p 8080:8080 tms/spring-boot-template
```

## References

- https://www.baeldung.com/spring-mvc-functional-controllers
- https://spring.io/guides/gs/testing-web/
- https://spring.io/guides/gs/spring-boot-docker/
- https://hub.docker.com/_/openjdk?tab=tags&page=1&name=11-
- https://hackmd.io/@ryanjbaxter/spring-on-k8s-workshop
- https://kubernetes.io/docs/tasks/access-application-cluster/configure-access-multiple-clusters/
- https://docs.docker.com/engine/reference/commandline/tag/
- https://docs.docker.com/engine/reference/commandline/push/






