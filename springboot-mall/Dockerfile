FROM openjdk:17-jdk-slim

# 安裝 maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# 工作目錄
WORKDIR /app

# 複製專案檔案進容器
COPY . /app

# 先 build 出 jar 檔
RUN mvn clean package -DskipTests

# 曝露 port，讓 Render 可以對外連線
EXPOSE 8080

# 啟動 Spring Boot（注意路徑要是你的 jar 檔）
CMD ["java", "-jar", "target/springboot-mall-0.0.1-SNAPSHOT.jar"]