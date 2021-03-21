FROM adoptopenjdk:11-jre-hotspot

VOLUME conf

EXPOSE 8080

COPY /target/charity-app-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=/conf/application.yml"]
