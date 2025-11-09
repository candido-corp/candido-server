# --------- BUILD STAGE ----------
FROM gradle:8.5.0-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN gradle build --dry-run
COPY src ./src
RUN gradle clean build -x test

# --------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/candido-server-*.jar /app/candido-server.jar
EXPOSE 8080
CMD ["sh", "-c", "sleep 15 && java -jar /app/candido-server.jar"]
