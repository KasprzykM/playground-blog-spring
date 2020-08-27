FROM openjdk:14
RUN mkdir -p /app
WORKDIR /app
COPY build/libs/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]