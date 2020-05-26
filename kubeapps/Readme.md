# Helm 3 command
kubectl create namespace kubeapps
helm install kubeapps --namespace kubeapps bitnami/kubeapps --set useHelm3=true




NAME: kubeapps
LAST DEPLOYED: Thu Apr 23 17:22:51 2020
NAMESPACE: kubeapps
STATUS: deployed
REVISION: 1
NOTES:
** Please be patient while the chart is being deployed **

Tip:

  Watch the deployment status using the command: kubectl get pods -w --namespace kubeapps

Kubeapps can be accessed via port 80 on the following DNS name from within your cluster:

   kubeapps.kubeapps.svc.cluster.local

To access Kubeapps from outside your K8s cluster, follow the steps below:

1. Get the Kubeapps URL by running these commands:
   echo "Kubeapps URL: http://127.0.0.1:8080"
   kubectl port-forward --namespace kubeapps service/kubeapps 8080:80

2. Open a browser and access Kubeapps using the obtained URL.


References: https://github.com/kubeapps/kubeapps



kubectl create clusterrolebinding example-kubeapps-applications-write --clusterrole=kubeapps-applications-write --serviceaccount default:example
kubectl create clusterrolebinding  kubeapp-edit --clusterrole=edit --serviceaccount default:kubeapps
 k describe secret  kubeapps-token-8jf26 -n default
