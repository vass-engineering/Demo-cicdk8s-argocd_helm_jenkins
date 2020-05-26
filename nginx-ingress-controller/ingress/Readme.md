

helm install nginx-ingress stable/nginx-ingress     --namespace ingress     --set controller.replicaCount=2     --set controller.nodeSelector."beta\.kubernetes\.io/os"=linux     --set defaultBackend.nodeSelector."beta\.kubernetes\.io/os"=linux


LAST DEPLOYED: Thu Apr 23 17:30:17 2020
NAMESPACE: ingress
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
The nginx-ingress controller has been installed.
It may take a few minutes for the LoadBalancer IP to be available.
You can watch the status by running 'kubectl --namespace ingress get services -o wide -w nginx-ingress-controller'

An example Ingress that makes use of the controller:

  apiVersion: extensions/v1beta1
  kind: Ingress
  metadata:
    annotations:
      kubernetes.io/ingress.class: nginx
    name: example
    namespace: foo
  spec:
    rules:
      - host: www.example.com
        http:
          paths:
            - backend:
                serviceName: exampleService
                servicePort: 80
              path: /
    # This section is only required if TLS is to be enabled for the Ingress
    tls:
        - hosts:
            - www.example.com
          secretName: example-tls

If TLS is enabled for the Ingress, a Secret containing the certificate and key must also be provided:

  apiVersion: v1
  kind: Secret
  metadata:
    name: example-tls
    namespace: foo
  data:
    tls.crt: <base64 encoded cert>
    tls.key: <base64 encoded key>
  type: kubernetes.io/tls



k get svc
NAME                            TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)                      AGE
nginx-ingress-controller        LoadBalancer   10.233.46.227   10.0.5.49     80:31783/TCP,443:30498/TCP   4m39s
nginx-ingress-default-backend   ClusterIP      10.233.3.25     <none>        80/TCP  
