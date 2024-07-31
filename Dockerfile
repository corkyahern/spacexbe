FROM eclipse-temurin
WORKDIR /opt/app
COPY target/spacex-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "/opt/app/spacex-0.0.1-SNAPSHOT.jar"]