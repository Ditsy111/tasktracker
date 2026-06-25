# ---------- Stage 1 : Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

COPY src src

RUN chmod +x mvnw && \
    ./mvnw clean package -DskipTests


# ---------- Stage 2 : Runtime ----------
FROM eclipse-temurin:21.0.7_6-jre

WORKDIR /app

RUN useradd -r -s /bin/false app

COPY --from=builder \
    /build/target/tasktracker-0.0.1-SNAPSHOT.jar \
    app.jar

RUN chown app:app app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]