# Rest Assured API testing [trello](https://trello.com/)

REST Assured is a Java library for validation of REST web services. It offers a friendly DSL (Domain-specific Languages) that describes a connection to an HTTP endpoint and expected results.

## Overview
In this project, we will deal with functional tests that do not work directly with Java classes. Instead, they connect to the HTTP endpoints offered by the application server and mimic the role of another client or the browser 

For this project, we will test a sample REST application that deals with boards. The API can be summarized as follows:

* [POST] (https://api.trello.com/1/boards/?key={key}&token={token}&name={name}) - create board.  
* [GET] (https://api.trello.com/1/boards/{id}?key={key}&token={token}) - get a board by id.  
* [PUT] (https://api.trello.com/1/boards/{id}?key={key}&token={token}) - update a board.  
* [DELETE] (https://api.trello.com/1/boards/{id}?key={key}&token={token}) - delete a board.   

All input and output of these calls is based on the JSON format. The REST client sends data to these endpoints in JSON format and the responses it gets are also in the JSON format. 


## Prerequisites
### API Keys and Token
Use [API_KEY_TOKEN](https://trello.com/app-key) to get API Key and Token.  

It is assumed that we already have a Java project that offers its services over HTTP/JSON when deployed to an application server. We will need:

0. A sample Java 8 project,
0. A valid pom.xml file that builds the project,
0. Maven installed (the command [mvn](https://maven.apache.org/install.html) should be available in your command line)
0. Internet access to download Maven dependencies.

## Project tools
#### REST Assured, TestNG, Hamcrest, Lombok, Jackson
 We need to modify our pom.xml file as follows:
```xml
 <dependencies>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.14.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.0.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.10.2</version>
        </dependency>
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-all</artifactId>
            <version>1.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.6</version>
        </dependency>
    </dependencies>

```
## Parallel execution method 
TestNG provides multiple ways to execute tests in separate threads. 
We should add [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html) to execute tests in separate threads
We need to add to our pom.xml:
```xml
   <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <groups>${groups}</groups>
                    <parallel>methods</parallel>
                    <threadCount>${threads}</threadCount>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
#### There are 3 different groups to execute in this project:
0. positive - execute all tests in PositiveApiTests class.
1. negative - execute all tests in NegativeApiTests class.
2. regression - execute all methods that have ```@Test``` annotation.

#### It's able to set the number of threads as well.
### Running with Docker
In this project, we will use the docker file to run it.  
Dockerfile is a plain text file with no extension present at the root directory of an application. This file contains a procedural script that needs to execute for the creation of the container that can build and run the application.
#### Requirments
0. Make sure you have Docker Engine installed in your system.
1. Create an account on [DockerHub](https://hub.docker.com/) 
#### Docker File
```
FROM maven:3.5.4-jdk-8-alpine as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
CMD mvn clean test -Dgroups=$GROUP -Dthreads=$THREAD
```
0. FROM: Tells the Docker to use the mentioned image as the base image. This image will be pulled from the docker hub.
1. COPY: Copies files/directory from source to destination path.
2. CMD: Provides the facility to run UNIX commands to start the container. This can be overridden upon executing the docker run command.
#### Create image
```
docker build -t project-with-docker .
```
To see all created images
```
docker images
```
#### Run image -> create docker container
```
docker run -e GROUP=regression -e THREAD=1 project-with-docker 
```
or
```
docker run -e GROUP=positive -e THREAD=2 project-with-docker
```
or
```
docker run -e GROUP=negative -e THREAD=3 project-with-docker 
```

## Usage
* Execute tests using ```positive``` group
```
cd RestAssuredApiTesting
mvn clean test -Dgroups=positive -Dthreads=4
```
#### An example of running tests from the positive group in 4 threads simultaneously
![Picture](https://s8.hostingkartinok.com/uploads/images/2020/12/acd8af1b7988b686231778eb5b5e6d74.png)
* Execute tests using ```negative``` group
```
cd RestAssuredApiTesting
mvn clean test -Dgroups=negative -Dthreads=9
```
#### An example of running tests from the negative group in 9 threads simultaneously
![Picture](https://s8.hostingkartinok.com/uploads/images/2020/12/b642fd1a9126f5650ba6857a437867f4.png)

* Execute tests using ```regression``` group
```
cd RestAssuredApiTesting
mvn clean test -Dgroups=regression -Dthreads=13
```
#### An example of running tests from the regression group in 13 threads simultaneously
![Picture]( https://s8.hostingkartinok.com/uploads/images/2020/12/7183713c3fcb00ff1a62119fdf22c003.png)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.