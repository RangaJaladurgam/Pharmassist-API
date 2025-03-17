# Stage 1: Build the JAR
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a smaller image with only the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy JAR from the builder stage
COPY --from=builder /app/target/Pharmassist-0.0.1-SNAPSHOT.jar app.jar

# Expose port (change if needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
