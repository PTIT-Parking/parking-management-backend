#Stage 1: build
#Start with Maven image that includes JDK 21

FROM maven:3.9.9-amazoncorretto-21-alpine AS build

#Copy source code and pom.xml file to /app folder

WORKDIR /app
COPY pom.xml .
COPY src ./src

#Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image (optional -> we can leverage JDK form maven image)
#Start with Amazon Corretto JDK 21
FROM amazoncorretto:21.0.6-alpine3.21

#Set working folder to App and copy compiled file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

#Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
