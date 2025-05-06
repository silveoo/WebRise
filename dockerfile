# Build stage
FROM eclipse-temurin:17 AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw -q -B dependency:go-offline
COPY src src
RUN ./mvnw -q -B package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/WebRise-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
