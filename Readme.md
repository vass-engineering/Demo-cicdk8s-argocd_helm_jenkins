DEMO CICD Kubernetes + Helm3 + Argo:
-------------------------
-------------------------

## Introduction:
In this topic we will see a little demo about how to integrate some technologies as Argo and Helm to help us to improve the control in our K8S through a simple CI CD workflow.


## Background:

Due the benefices and popularity of Micro-services infrastructures, the number of this kind of infrastructures is growing fast, demanding full control,  scalability, and a faster continues integration & continuous deployment method. This kind of demands, required new technologies and new working methods. In this topic, we will focus in a little demo about how some technologies will help us to speed up our Deployment Life Cycle.

* We will use the next technologies:
  * Kubernetes: Infrastructure of containerized applications
  * Image registry: Harbor
  * Pipelines CICD: Jenkins
  * HELM: Kubernetes template language. We will not create helm packages.
  * ARGO: CD: Declarative, GitOps continuous delivery tool
  * GIT: source-of-truth


## Design criteria:

After some years maintaining Kubernetes infrastructures, you will be able to see different ways to deploy applications, although not the best one, the most common practices that we have seen is the use of templates for the first deployments, and after that, the changes in  the objects created from this templates are totally manually,  in essential, every change in the manifest after the first deployment is manually done and  directly in the kuberentes object. In this scenario, the only way to recover and object corrupted or deleted must be using a backup of the objects. Not misunderstanding me, a backup is always required, but it will not be the way to recover an object delete, or at least, not the first and only method. We should think about create the objets from a source of true in case they not exists, corrupted or accidentally deleted. If we deploy our manifest from a source of true as git, we keep track of every change, we will be able to recover any object in any moment and not just to recover, we will be able to recreate a deployment in other infrastructure just changing the endpoint, (I know, I know... the persistent information, the database...but at least the deployment is done). A lot of teams are already working like that, but not all of them are using technologies as Argo, which allow you not just to deploy from  a source of true, it will be taking care of every deployment, it will watch that  in every moment the state of the deployment is as it should be, warning you in  case of mismatching or
desynchronization.


## Workflow CICD

![alt text](https://github.com/vass-engineering/DemoSolutions/blob/master/DocsImages/CICDTMS.png)


* The CICD is managed from Jenkins, where we will have a pipeline to deploy our application, the pipeline will be a Pipeline script from SCM.
* Regarding the pipelines, you can choose between one pipeline for every application, or just one pipeline for all your applications.  
  * The pipeline will allow us to change the next variables:
    * APP_REPO: Git URL.
    * APP_BRANCH: Git Branch.
      * In the demo we will have every thing in one git, but you can separate the code of the application(java app) from the code of the infrastructure(helm templates). If you want to separate,  you will need to add two variables to the pipeline 1.INFRA_REPO, 2.INFRA_BRANCH (for example), and one step to download from this new repo.
    * VALUES_APP: The values for our application, the helm template will parameterized with the values from this file. In demo we have "valuesdev.yaml", it will be the values for the DEV, and our recommendation as an easy ways to maintaining, one values<environment>.yaml for one environment.
    * NAMESPACE: The NameSpaces where you will deploy.
    * REGISTRY: Registry where  is going to be our images.
    * REPOSITORY: Repository inside the Registry
    * NAME_APP: Name for the application. Argo will  register the APP with this name.
    * OPTIONDEPLOY: We have defined two roads, you will select one of them.
      * Build & Deploy: To build a new image from the code and deploy this images.
      * Deploy: Select an image from the registry and deployment.

![alt text](https://github.com/vass-engineering/DemoSolutions/blob/master/DocsImages/PipelineSelectOptions.png)  

### Steps ROAD Deploy:

1) Obtain images from registry: This step, using the API of Harbor, obtain the images allocated in our Harbor registry.
2) Select images from Harbor: You will select the image that you want to deploy.

![alt text](https://github.com/vass-engineering/DemoSolutions/blob/master/DocsImages/SelectImagefromRegisty.png)  

3) SetImage in VALUES_APP: It will change the parameter tag from the VALUES_APP. It will change tag value in the file, and push the change to Git.
4) Check if namespace exists: It will check if the namespace exists in our cluster and it will be created in cases is not present. (This is a demo, in production add step where somebody approve the creation)
5) Register the  application Argo: In this step we will register the APP in Argo. It is not a problem in case of the APP is already registered.


### Steps ROAD Build & Deploy

1) Compile and test the code: Compile and test the artifacts.
2) Building image: In this step we build our new images.
3) Push Image to registry: Allocated our image in the registry, in our case, in Harbor.
4) SetImage in VALUES_APP: It will change the parameter tag from the VALUES_APP. It will change tag value in the file, and push the change to Git.
5) Check if namespace exists: It will check if the namespace exists in our cluster and it will be created in cases is not present. (This is a demo, in production add step where somebody approve the creation)
6) Register the  application Argo: In this step we will register the APP in Argo. It is not a problem in case of the APP is already registered.


At the end of both Roads, Argo will take care of our application.

* Enjoy the view console from ArgoCD, how automatically is synchronized or created our application.

![alt text](https://github.com/vass-engineering/DemoSolutions/blob/master/DocsImages/ArgoCD.png)



## Day two Operations

From my point of view, the pipeline should be as easy as you can see, I build an image and deploy or I select an image and I deploy. But there are a lot of values in the values.yaml, and from the pipeline we just managed the tag of the Image. There is a lot of solution to control the rest of the variables, from my humble opinion, keep it easy, Operations teams should managed the values.yaml directly on Git. Git allow you to control all the changes and keep the control of who made the change. So Operations team should be responsible to keep the infrastructure code and apply the changes on it. For production environment you may register  the applications in  Argo as  SYNC POLICY: Manual, where somebody with privileges should approve the synchronized from the console or from a pipeline.



## Technologies Background

If you want to see in details the steps, continuous reading.


### Harbor

* Definition from https://goharbor.io/: Harbor is an open source registry that secures artifacts with policies and role-based access control, ensures images are scanned and free from vulnerabilities, and signs images as trusted.
* CNCF Graduated Project

There is a lot information about this Graduated project in the CNFC and a lot of website telling you how to deploy. We use helm template:


```
helm install  harborvassregistry harbor/harbor  --set chartmuseum.enabled=false  --set externalURL=https://harbor.192.168.66.80.xip.io --set expose.ingress.hosts.core=harbor.192.168.66.80.xip.io   --set persistence.persistentVolumeClaim.registry.size=8Gi --set expose.ingress.annotations."kubernetes\.io/ingress\.class"=ingressinterno  --set notary.enabled=false
```


### Helm + Argo. Perfect match :)

* Let's see one definition for Helm: Helm is Helm is a package manager for Kubernetes. Helm is the K8s equivalent of yum or apt. Helm deploys charts, which you can think of as a packaged application.
* Let's see one definition for Argo: It is a declarative, GitOps continuous delivery tool for Kubernetes.
* Ok, Helm was thought to create packages and deploy these packages created, this is the perfect case of use. But we can used in a another usefully way. Helm allow us to create kubernetes objects based on a template and defined variables from a yaml file know as the values.yaml. Futhermore, the integration with ArgoCD is just perfect. We can define and create an  ArgoCD application where our kubernetes objets are define in helm template language, allocated in GitHub (Source of true), and  it is parameterized with the values.yaml, even more, same template to create the objets, but one valuesdev.yaml for dev environment, valuesprod.yaml for production environment.

*In this demo we just define one values.yaml named valuesdev.yaml, in a production environment, you can defined your pipeline in different ways, allow the developer to select one values.yaml with one by default, or just define the variable in the jenkinsfile as a permanent value.*

We create or declare the ArgoCD app in one of the steps of the pipeline, as easy as you can see, the application will be created, but if the application already exists there is not problem as we are not changing anything in the definition of the application, we will change the values of the repository and Argo will synchronizes the deployment with this new image.

```
 argocd app create "${params.NAME_APP}" --repo  ${params.APP_REPO} --revision  ${params.APP_BRANCH}   --path helm_templates/helm3-java --dest-server https://kubernetes.default.svc --dest-namespace ${params.NAMESPACE} --project default --values ${params.VALUES_APP} --sync-policy automated --auto-prune
```

${params.NAME_APP}= Name of the application, defined in Jenkins.
${params.APP_REPO}= Where is going to be out helm template.
${params.APP_BRANCH} = Branch of Where is going to be out helm template.
${params.NAMESPACE}= Namespace where we will create the app.
${params.VALUES_APP}= values.yaml  file that we will use for our Application.



## FAQ

* Where is my code as a developer?
  * Your code will be in Git.

* What I should put on my code as a developer?
  * Your code app. (Java in this example)
  * A Dockerfile:
    * The pipeline will build a package  (mvn clean package -f tms-spring-boot-template), and copy the package in the image build base on the dockerfile.

* Where is my code as a operator"?
  * Your code will be in Git.

* What I should put on my code as an operator?
  * helm helm_templates
    * We will add a values.yaml for every environment.
  * jenkinsfile

* What happen if somebody delete an object from K8s (the services, ingress).
  *  ArgoCD have some different ways to works, basically, once the object is delete, ArgoCD will warning you about the desynchronization  waiting for your feedback or  it will force a synchronized recreating the objetc, it depends on ARGOCD policy sync definition in the app.

* What happen when I create an application in ARGO?
  * ArgoCD will create an object named "applications.argoproj.io" with all the information that we providfed in the moment that we will created and the staus.

* What happen when I delete an applications.argoproj.io  object?
  * The objects of the deployment (service, ingress...),will not be delete, but for Argos the app has disappear.


*There is some strategies where you can keep this object in Git, it depends on you strategies,  you could create the application Argo definition base on that file*
