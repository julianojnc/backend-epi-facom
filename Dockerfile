FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests
RUN apt-get update && apt-get install -y nano
EXPOSE 4000 8080
ENTRYPOINT [ "java", "-jar", "target/api-0.0.1-SNAPSHOT.jar" ]