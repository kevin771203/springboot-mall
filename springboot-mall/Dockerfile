FROM openjdk:17-jdk-slim

# 安裝 Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# 設定容器中的工作目錄
WORKDIR /app

# 複製 pom.xml 和 source code（如果你想在容器內建置或測試）
COPY . /app

# 如果只跑已建置好的 jar，保留這行
# COPY target/springboot-mall-0.0.1-SNAPSHOT.jar /app/springboot-mall.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "springboot-mall.jar"]
