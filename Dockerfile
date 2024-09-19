FROM gradle:8.5.0-jdk21 AS build

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src

RUN gradle clean build

FROM amazoncorretto:21
COPY --from=build /app/build/libs/candido-server-*.jar /app/candido-server.jar
EXPOSE 8080
CMD sleep 15 && java -jar /app/candido-server.jar
