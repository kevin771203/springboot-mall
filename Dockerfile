FROM openjdk:17-jdk-slim

# 安裝 Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app

# 複製專案檔案
COPY . /app

# 編譯並打包成 jar
RUN mvn clean package -DskipTests

# 曝露 port 給 Render
EXPOSE 8080

# 執行 jar
CMD ["sh", "-c", "java -jar $(find target -name \"*.jar\" | head -n 1)"]
