# Use a base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file into the container
COPY target/coffeeshop-0.0.1-SNAPSHOT.jar app.jar
# Expose the application port
EXPOSE 8080
# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]