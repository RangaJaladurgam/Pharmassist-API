# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (to cache dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this helps in caching)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built jar file 
COPY target/Pharmassist-0.0.1-SNAPSHOT.jar.original app.jar

# Expose the application port
EXPOSE 7000

# Run the application
CMD ["java", "-jar", "app.jar"]
