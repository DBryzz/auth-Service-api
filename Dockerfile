
#Our Base Image
FROM tomcat:8.5-alpine

EXPOSE 8080

#Copy WebApp
COPY target/auth-service-api.jar /home/api-security/auth-service-api.jar

ENTRYPOINT ["java", "-jar", "/home/api-security/auth-service-api.jar"]
