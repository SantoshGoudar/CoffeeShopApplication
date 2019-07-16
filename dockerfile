FROM openjdk:8-jdk-alpine
ADD target/CoffeeShopApplication-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar