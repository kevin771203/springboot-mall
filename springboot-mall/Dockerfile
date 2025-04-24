# 使用官方的 Java 基底映像來建立容器
FROM openjdk:17-jdk-slim

# 設定容器中的工作目錄
WORKDIR /app

# 複製你的 Spring Boot JAR 檔案到容器中
COPY target/springboot-mall-0.0.1-SNAPSHOT.jar /app/springboot-mall.jar

# 設定環境變數（如果需要）
ENV SPRING_PROFILES_ACTIVE=prod

# 開放容器的埠號
EXPOSE 8080

# 設定容器啟動時執行的指令
ENTRYPOINT ["java", "-jar", "springboot-mall.jar"]