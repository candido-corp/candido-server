FROM amazoncorretto:21
WORKDIR /app
COPY ./build/libs/server-*.jar /app/server.jar
EXPOSE 8080
CMD sleep 5 && java -jar /app/server.jar
