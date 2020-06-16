# Spring Boot Application with DATA REST + MYSQL deployed on Kubernetes
This application is showcases the following
- Create a Basic Spring Boot Application
- Create a Repository and Entity for Student
- Create RESTful CRUD operations for **Student** using Spring DATA REST
- Use MySQL as database
- Build and Test application locally
- Create a docker image and push it to docker hub.
- Deploy a mysql database in Kubernetes
- Deploy the application in Kubernetes and connect to the MySQL database already deployed

## Building the application
- Download the application code from github
- Ensure that **docker**, **gradle** and **Java 11** are already installed
- Ensure that MySQL Database is already installed
- Go to the application git folder
- Open the application.properties and ensure that **For Testing locally** section is uncommented and **For Deployment in Kubernetes** section is commented
- Execute the following command "gradlew clean build" in windows or "./gradlew clean build" in linux
- To start the application excute the gradle **bootRun** task. It would open the application at "http://localhost:8080"
- All the crud operations could be done at  "http://localhost:8080/student"

## Dockerizing the application
- Stop the application by terminating the gradle **bootRun** task if it is running
- Open the application.properties and ensure that **For Testing locally** section is commented and **For Deployment in Kubernetes** section is uncommented
- Execute "gradlew clean build -x test"
- **Dockerfile** is already available for docker build
- Execute "docker build -t [your-docker-user-name]/mysql-app:v1 ."
- Ensure that the image got created by executing "docker images"
- Push the image to docker hub by executing "docker push <your-docker-user-name>/mysql-app:v1"

## Deploying in Kubernetes
- Use any Kubernetes deployment. My choice is **Minikube**
- Start Minikube
- Create a directory called "/mnt/data" for persistent volume
- Execute the following commands one after another in sequence
   * kubectl apply -f deployments/mysql-pv.yaml
   * kubectl apply -f deployments/mysql-pvc.yaml
   * kubectl apply -f deployments/mysql-deployment.yaml
   * kubectl apply -f deployments/mysql-service.yaml
 
 All these would ensure that Persistent Volume, MySQL deployment are created. The MySQL deployment is also exposed as a service called **mysql** which we are referring in our SPRING_DATASOURCE_URL section without any port
 
 - To access the mysql service execute the command mentioned in the **mysql-client.txt**  file under deployments folder
 - To deploy the application first open the mysql-app-deployment.yaml and replace **adityapratapbhuyan/mysqlapp:no-port** with **docker push [your-docker-user-name]/mysql-app:v1**
 - Execute  "kubectl apply -f deployments/mysql-app-deployment.yaml". It would take around 2-3 minutes for the first time to download the image and deploy. Ensure the image started successfully by executing "kubectl get deploy"
 - Once the Deployment for the app is running, execute the below command to expose the deployment as a service
 **kubectl -f deployments/mysql-app-service.yaml**
 - Once the service is created the service could be accessed by executing "minikube service mysql-app". It would open the service endpoint on the browser. Then the testing could be done on the url opened in the browser.   
