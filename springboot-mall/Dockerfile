#FROM openjdk:17-jdk-slim
#
#RUN apt-get update && \
#    apt-get install -y maven && \
#    apt-get clean
#
#WORKDIR /app
#COPY . /app
#
#CMD ["mvn", "test"]

FROM openjdk:17-jdk-slim

# 安裝 Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app

# 複製專案檔案
COPY . /app

# 編譯並打包成 jar（你也可以用預設的 mvn spring-boot:run）
RUN mvn clean package -DskipTests

# 曝露 port 給 Render 掃到（你 properties 裡有設定 server.port=${PORT:8080}）
EXPOSE 8080

# 執行 jar（根據你打包出來的 jar 名字調整）
CMD ["java", "-jar", "target/your-jar-name.jar"]
