# ---------------------------------------------------
# Stage 1: Build the application
# ---------------------------------------------------
# Use JDK 17 or 21 depending on your Spring Boot version (e.g. eclipse-temurin:21-jdk-alpine)
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copy Maven/Gradle wrapper and dependency descriptors first to leverage Docker layer caching
# --- For Maven:
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# --- For Gradle (uncomment if using Gradle):
# COPY gradlew build.gradle settings.gradle ./
# COPY gradle gradle
# RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests
# For Gradle: RUN ./gradlew bootJar -x test --no-daemon

# ---------------------------------------------------
# Stage 2: Minimal runtime image
# ---------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Run as non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser:appgroup

# Copy built JAR from builder stage
COPY --from=builder /build/target/*.jar app.jar
# For Gradle: COPY --from=builder /build/build/libs/*-SNAPSHOT.jar app.jar

# Default Spring Boot port (adjust if you use another port in application.properties)
EXPOSE 8080

# WebSocket connections require stable memory allocations; container-aware flags ensure proper sizing
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
