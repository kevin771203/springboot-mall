# 使用 Maven 來建構應用程式
FROM maven:3.8.4-openjdk-17-slim AS builder

# 設定工作目錄
WORKDIR /app

# 複製 pom.xml 和程式碼
COPY pom.xml ./
COPY src ./src

# 使用 Maven 建構 Spring Boot 應用程式
RUN mvn clean package -DskipTests

# 使用官方的 Java 基底映像來建立容器
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製建構後的 JAR 檔案
COPY --from=builder /app/target/springboot-mall-0.0.1-SNAPSHOT.jar /app/springboot-mall.jar

# 設定環境變數
ENV SPRING_PROFILES_ACTIVE=prod

# 開放容器的埠號
EXPOSE 8080

# 啟動應用程式
ENTRYPOINT ["java", "-jar", "springboot-mall.jar"]