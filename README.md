## About this project
This is a spring boot rest projects that combines both UI and the REST web services, and docker image files.
(It is also Maven enabled and functional)

## Assumed  knowledge
Java, Eclipse, Git, Basic knowledges of SpringBoot, Gradle, Docker.
 
## Environment
I am using 
	Eclipse IDE for Enterprise Java Developers( It has built-in Docker & git plugin):
	Version: 2019-03 (4.11.0)
	Build id: 20190314-1200
	OS: Windows 10, v.10.0, x86_64 / win32
	Java version: 1.8.0_201-1-ojdkbuild

I have following plugins installed:
- Buildship Gradle Integration 3.0
- Spring Tools 4 - for Spring Boot (aka Spring Tool Suite 4） 4.22 RELEASE
	

  [Git clone](https://github.com/jywang919/otm-support) the project into Eclipse.
  Right Click the project -> Configure -> Add Gradle Nature.
  You may need to re-start Eclipse for Spring Boot/Gradle tools to show-up
## Enable hot code swapping in eclipse
	Run As -> Run Configurtions -> Arguments -> VM Arguments add following -javaagent:/Users/jiyongwangMBPro/git/otm-support/lib/springloaded-1.2.5.RELEASE.jar -noverify
	
## Run the application in Eclipse on your local machine
Right click the project, Run As: Spring Boot App

## Run the application on Command line
Assuming the project code is cloned at: H:\git\spring-boot-rest-docker-example
##### H:\git\spring-boot-rest-docker-example>gradlew bootRun

## Test the application that is running on local machine
http://localhost:8080, and you should see the jsp page.

## Test the REST web service:
http://localhost:8080/aocs, and you will see the empty results

## Post data to the REST service:  - See StatController/GpsController for instructions

In PostMan,
Url: http://localhost:8080/aoc

- Header:  
       Key: content-Type,
        Value: application/json

- Body:    
      provide following raw data:
      
      { "lastName":"Doe" ,"firstName":"John" ,"age": 26 ,"note":"He is smart" }

- Re-fresh http://localhost:8080/aocs and you will see the newly posted data
 
 
## Run inside a Docker
1.	Build the war:
	Gradle Tasks -> Build -> Clean
	Gradle Tasks -> Build -> war

2.	Build the docker image	
	With Docker Deamon running on your machine.
	Right click Dockerfile -> Run As -> Docker Image Build and give aocspringboot as the image name
	(You can also build the Docker image using docker image build command within your Docker environment after copying the war file into the a location a in your VM and modify the Dockerfile to reflect the war location)  
	
3.	docker container run -p 8080:8080 -d aocspringboot

4.	Check that the app is running:
	http://<IP of your docker VM>:8080 e.g. http://192.168.99.100:8080 
	
5.	To post data to the WS, change the URL in PostMan above	




