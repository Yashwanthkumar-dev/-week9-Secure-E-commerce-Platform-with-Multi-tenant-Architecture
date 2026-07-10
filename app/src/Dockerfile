FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8081

CMD ["java","-jar","target/app-0.0.1-SNAPSHOT.jar"]