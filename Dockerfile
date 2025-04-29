# -------- BUILD STAGE --------
FROM gradle:8.5.0-jdk21 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies
COPY src ./src
RUN gradle clean bootJar -x test

# -------- RUNTIME STAGE --------
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/candido-server-*.jar candido-server.jar
EXPOSE 8080
CMD ["java", "-jar", "candido-server.jar"]
