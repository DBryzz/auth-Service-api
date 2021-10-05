
# Auth-Service-API

## About Auth-Service-API 

Just as the name suggest, Auth-Service-API is a REST API that provides authentication and authorization services. 
A user may create an account, login to his/her account, change password, reset password using email. 

## Technologies
- Springboot
- MySQL
- 3Docker
- Intelli J Idea <br/>

## [ WARNING ] ##
Never expose your credential as is the case in the **mail.properties** Configuration. <br/>
If possible never commit this file on a public repository or cloud.

## Running the application

There are a number of ways one could run the application including <br/>
1 - Using intelliJ. <br/>
2 - Building the application into a jar file and then executing it. <br/>
3 - Usage of docker and docker-compose<br/><br/>

### Creating executable
 From the root directory build the project using the command  
 **./mvnw clean package -DskipTests**. <br/>
 
### Run using IntelliJ Idea
1 - Clone the repository and checkout to master or download master branch. <br/>
2 - Open application with intelliJ and move to the root directory (auth-service-api) <br/>
3 - Install all dependencies by running **mvn clean install** <br/>
4 - Select profile to run (test, dev or prod) by changing setting in application.properties <br/>
**spring.profiles.active=test** or **spring.profiles.active=dev** or **spring.profiles.active=prod** <br/>
Test uses h2, dev uses mysql docker image while prod uses MySQL<br/>
5 - Run application by pressing run icon or by using the key combination **shift + F10** <br/>


## Using Executable.
1 - Build the application as described above <br/>
2 - Run executable using the command <br/>
**java -jar target/**

## Using Docker.
1 - Switch to dev profile by making sure the active profile in application.properties is dev <br/>
**spring.profiles.active=dev** <br/>
2 - Run **docker-compose build** </br> 
3 - Run **docker-compose up** </br>



