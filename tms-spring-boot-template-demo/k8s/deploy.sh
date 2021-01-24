#!/bin/sh

set -o errexit

KUBECMD="kubectl"

echo "deleting previous versions"
$KUBECMD delete all --selector=app=tms-spring-boot-template2
$KUBECMD delete ingress --selector=tms-spring-boot-template2
echo "previous version deleted"

echo "create deployment"
$KUBECMD create -f deployment.yml
echo "deployment created"

echo "create service"
$KUBECMD create -f service.yml
echo "service created"

echo "create ingress"
$KUBECMD create -f ingress.yml
echo "ingress created"
