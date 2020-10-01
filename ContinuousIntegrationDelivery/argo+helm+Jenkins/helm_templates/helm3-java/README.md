# Java Helm Chart

Deploy your springBoot java application using this template.


## Table of Content

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [Java Helm Chart](#java-helm-chart)
  - [Table of Content](#table-of-content)
  - [Prerequisites](#prerequisites)
  - [Configuration](#configuration)
  - [Installation](#installation)
    - [Ingress](#ingress)
      - [Hosts](#hosts)
      - [Extra Paths](#extra-paths)
      - [Annotations](#annotations)
      - [Example Ingress configuration](#example-ingress-configuration)
    - [Configmaps](#configmaps)
  - [Uninstalling the Chart](#uninstalling-the-chart)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Prerequisites

- Kubernetes 1.12+
- Helm 2.11+ or Helm 3.0-beta3+

## Configuration

The following table lists common configurable parameters of the chart and
their default values. See values.yaml for all available options.

| Parameter                               | Description                                                                 | Default                                       |
| --------------------------------------- | --------------------------------------------------------------------------- | --------------------------------------------- |
| `replicaCount`                          | Number of pods                                                              | `1`                                           |
| `configmaps.enabled`                    | Create configmap                                                            | `false`                                       |
| `configmaps.configmapsfiles`            | Template path where helm must find configmaps that will be created          | `configmaps`                                  |
| `configmaps.configmapname`              | Configmap Name                                                              | `configmaptest`                               |
| `configmaps.configmappath`              | Path where Configmaps will be mounted in the Deployment                     | `/tmp`                                        |
| `image.repository`                      | Registry for images                                                         | `evomsaacr.azurecr.io`                        |
| `image.pullPolicy`                      | Container pull policy                                                       | `Always`                                      |
| `image.tag`                             | Container image to use                                                      | `latest`                                      |
| `healthChecks.livenessDelaySeconds`     | LivenessDelaySeconds                                                        | `180`                                         |
| `healthChecks.readinessDelaySeconds`    | ReadinessDelaySeconds                                                       | `150`                                         |
| `envs.springProfileActive`              | springProfileActive                                                         | ``                                            |
| `envs.springConfigLocation`             | Path in the pod where spring configuration properties will be stored        | ``                                            |
| `envs.serverServletContextPath`         | App ContextPath                                                             | ``                                            |
| `envs.contextPathHealthChecks`          | HealthChecks ContextPath                                                    | `/`                                           |
| `envs.gz_max_metaspace_size`            | gz_max_metaspace_size                                                       | `200`                                         |
| `envs.java_options`                     | ReadinessDelaySeconds                                                       | ``                                            |
| `appdynamics.enabled`                   | Enable Appdynamics                                                          | `false`                                       |
| `appdynamics.applicationName`           | Appdynamics application name                                                | ``                                            |
| `appdynamics.tierName`                  | Appdynamics tierName                                                        | ``                                            |
| `appdynamics.accountAccessKey`          | Appdynamics accountAccessKey                                                | ``                                            |
| `appdynamics.accountName`               | Appdynamics accountName                                                     | ``                                            |
| `appdynamics.hostName`                  | Appdynamics hostName                                                        | ``                                            |
| `imagePullSecrets`                      | Secret name to pull and push images from ACR                                | `evomsaacr-secret`                            |
| `nameOverride`                          | String to partially override java.fullname template                         | ``                                            |
| `fullnameOverride`                      | String to fully override java.fullname template                             | ``                                            |
| `serviceAccount.create`                 | Specifies whether a service account should be created                       | `true`                                        |
| `serviceAccount.annotations`            | Annotations to add to the service account                                   | ``                                            |
| `serviceAccount.name`                   | The name of the service account to use                                      | ``                                            |
| `podSecurityContexte`                   | fsGroup                                                                     | ``                                            |
| `securityContext`                       | capabilities                                                                | ``                                            |
| `service.type`                          | Kubernetes Service type                                                     | `ClusterIP`                                   |
| `service.port`                          | Service HTTP port                                                           | `8080`                                        |
| `ingress.enabled`                       | Enable ingress controller resource                                          | `false`                                       |
| `ingress.enabledtls`                    | Enable ingress TLS controller resource                                      | `false`                                       |
| `ingress.annotations`                   | Ingress annotations                                                         | ``                                            |
| `ingress.hosts[0].host`                 | Default host for the ingress resource                                       | `chart-example.local`                         |
| `ingress.hosts[0].paths[0]`             | Path within the url structure                                               | `[]`                                          |
| `ingress.tls[0].hosts[0]`               | TLS hosts                                                                   | `[]`                                          |
| `ingress.tls[0].secretName`             | TLS Secret (certificates)                                                   | `[]`                                          |
| `resources`                             | CPU/Memory resource requests/limits                                         | ``                                            |
| `nodeSelector`                          | Node labels for pod assignment                                              | `{} (The value is evaluated as a template)`   |
| `tolerations`                           | Tolerations for pod assignment                                              | `[] (The value is evaluated as a template)`   |
| `affinity`                              | Map of node/pod affinities                                                  | `{}`                                          |


Specify each parameter using the `--set key=value[,key=value]` argument to
`helm install`.


## Installation

```shell
helm install --name java-example -f custom.yaml .
```


### Ingress

This chart provides support for ingress resources. If you have an ingress controller installed on your cluster, such as [nginx-ingress](https://hub.kubeapps.com/charts/stable/nginx-ingress) or [traefik](https://hub.kubeapps.com/charts/stable/traefik) you can utilize the ingress controller to expose Kubeapps.

To enable ingress integration, please set `ingress.enabled` to `true`

#### Hosts

Most likely you will only want to have one hostname that maps to this JAVA installation, however, it is possible to have more than one host. To facilitate this, the `ingress.hosts` object is an array.  TLS secrets referenced in the ingress host configuration must be manually created in the namespace.

In most cases, you should not specify values for `ingress.hosts[0].serviceName` and `ingress.hosts[0].servicePort`. However, some ingress controllers support advanced scenarios requiring you to specify these values. For example, [setting up an SSL redirect using the AWS ALB Ingress Controller](https://kubernetes-sigs.github.io/aws-alb-ingress-controller/guide/tasks/ssl_redirect/).

#### Extra Paths

Specifying extra paths to prepend to every host configuration is especially useful when configuring [custom actions with AWS ALB Ingress Controller](https://kubernetes-sigs.github.io/aws-alb-ingress-controller/guide/ingress/annotation/#actions).

```shell
helm install --name my-java . \
  --set ingress.enabled=true \
  --set ingress.hosts[0].name=java.domain.com \
  --set ingress.extraPaths[0].service=ssl-redirect \
  --set ingress.extraPaths[0].port=use-annotation \
```

#### Annotations

For annotations, please see [this document for nginx](https://github.com/kubernetes/ingress-nginx/blob/master/docs/user-guide/nginx-configuration/annotations.md) and [this document for Traefik](https://docs.traefik.io/configuration/backends/kubernetes/#general-annotations). Not all annotations are supported by all ingress controllers, but this document does a good job of indicating which annotation is supported by many popular ingress controllers. Annotations can be set using `ingress.annotations`.

#### Example Ingress configuration

```shell
helm install --name my-java . \
  --set ingress.enabled=true \
  --set ingress.hosts[0].name=java.domain.com \
  --set ingress.hosts[0].path=/
  --set ingress.hosts[0].tls=true
  --set ingress.hosts[0].tlsSecret=java.tls-secret
```

### Configmaps

The chart is ready to create configmaps allocated in the template path "configmaps" when variable "configmaps.enabled" is set to true. Allocate your configmaps in the template path "configmaps" with your cycle CD.

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```console
$ helm delete my-release
```
