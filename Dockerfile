FROM openjdk:17-slim
MAINTAINER alnoman.cse@gmail.com
EXPOSE 8080
COPY build/libs/app.jar .
CMD ["java", "-XX:MaxRAMPercentage=80.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]