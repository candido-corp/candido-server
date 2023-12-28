FROM gradle:8.5.0-jdk21 AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle clean build

FROM amazoncorretto:21
COPY --from=build /app/build/libs/server-*.jar /app/server.jar
EXPOSE 8080
CMD sleep 5 && java -jar /app/server.jar
