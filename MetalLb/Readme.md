
# Deploy Metal LB Solution in k8s Bare-metal

* Configure your configlb.yaml, add your IP Range Addresses. Here you will find two examples in ./objects/RHELInternalVass/configlbRhelVass.yaml  and  ./objects/VagrantLabs/configlbvagrant.yaml.

```
kubectl create -f metallb.yaml
k config set-context --current --namespace=metallb-system
k create -f <configlb.yaml>
```

*
