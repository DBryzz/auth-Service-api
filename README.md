
# Auth-Service-API

## About Auth-Service-API

Just as the name suggest, Auth-Service-API is a REST API that provides authentication and authorization services. 
A user may create an account, login to his/her account, change password, reset password using email. 

## Technologies
- Springboot
- MySQL
- 3Docker
- Intelli J Idea

## Running the application

There are a number of ways one could run the application including
1 - Using intelliJ. 
2 - Building the application into a jar file and then executing it. 
3 - Usage of docker and docker-compose

### Creating executable
 From the root directory build the project using the command  
 **./mvnw clean package -DskipTests**.
 spring-boot:run spring-boot:repackage spring-boot:build-image package
### Run using IntelliJ Idea
1 - Clone the repository and checkout to master or download master branch.
2 - Open application with intelliJ and move to the root directory (auth-service-api)
3 - Install all dependencies by running **mvn clean install**
4 - Select profile to run (test, dev or prod) by changing setting in application.properties
**spring.profiles.active=test** or **spring.profiles.active=dev** or **spring.profiles.active=prod**
Test uses h2, dev uses mysql docker image while prod uses MySQL
5 - Run application by pressing run icon or by using the key combination **shift + F10**


## Using Executable.
1 - Build the application as described above
2 - Run executable using the command 
**java -jar target/

1 - Clone the repository and checkout branch to-do or download to-do branch.

2 - Run composer install to install all dependencies in repo's root directory.

3 - Run php artisan serve </br> </br>

## Running the application using docker-compose

1 - Make sure docker is setup on your local machine.

2 - Clone repository and checkout to-do branch.

3 - Make sure you are in repo's root directory, then run <i>composer install</i> to install all dependencies.

4 - Change database parameters in .env file to <br/>
<i>DB_CONNECTION=mysql </br>
DB_HOST=mysql</br>
DB_PORT=3306 </br>
DB_DATABASE=todo</br>
DB_USERNAME=root</br>
DB_PASSWORD=secret</i>

5 - Run <i>docker-compose build</i> to build services

6 - Run <i>docker-compose up</i> to run application.

7 - Access application at localhost:8088 </br><br/>

## Running the code
  
    - Create the docker image using the command
      **sudo docker build -t auto-grader .**
    - Start the application using the command
      **sudo docker-compose up**
