FROM maven:3.8.1-openjdk-17 AS builder
 
WORKDIR /app
 
COPY pom.xml .
RUN mvn dependency:go-offline -B
 
COPY src ./src
RUN mvn clean package -DskipTests
 
FROM openjdk:17
WORKDIR /app
 
# Copy the built jar file from the build stage
COPY --from=builder /app/target/*.war user.war
 
EXPOSE 9093:8080
 
# Define the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "user.war"]
