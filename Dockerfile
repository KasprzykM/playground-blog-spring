FROM openjdk:14
RUN mkdir -p /app
WORKDIR /app
COPY build/libs/*.jar ./app.jar
EXPOSE 8080
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]