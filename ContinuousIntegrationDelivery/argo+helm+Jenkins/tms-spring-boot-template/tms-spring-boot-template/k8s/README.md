# Notes

## Preliminar tasks

```
mvn clean package
```

## Docker

```
docker login harbor.52.209.128.30.nip.io/harborvass
docker build -t harbor.52.209.128.30.nip.io/harborvass/tms-spring-boot-template:0.1.0 .
docker images
docker push harbor.52.209.128.30.nip.io/harborvass/tms-spring-boot-template:0.1.0
```

```
kubectl create secret docker-registry harborvass \
    --namespace tms-dev \
    --docker-server=harbor.52.209.128.30.nip.io \
    --docker-username=harborvass \
    --docker-password=H4rb0rv4ss20
kubectl get secrets
```

## Kubernetes

```
kubectl get all -n tms-dev
kubectl get pods -n tms-dev
kubectl get svc -n tms-dev
kubectl get ingress -n tms-dev

kubectl get pod --selector=app=tms-spring-boot-template2
kubectl logs tms-spring-boot-template2-5d7dcd9b5b-zpnkn
kubectl get deployment -n tms-dev tms-spring-boot-template2 -o yaml
kubectl get svc -n tms-dev tms-spring-boot-template2 -o yaml
kubectl get ingress -n tms-dev tms-spring-boot-template2 -o yaml
```

## References

- https://kubernetes.io/docs/reference/kubectl/cheatsheet/
- https://kubeyaml.com/
