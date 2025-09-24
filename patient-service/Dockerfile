# Build stage
FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY . .
RUN ls
RUN gradle bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]