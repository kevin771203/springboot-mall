FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/springboot-mall-0.0.1-SNAPSHOT.jar springboot-mall.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springboot-mall.jar"]