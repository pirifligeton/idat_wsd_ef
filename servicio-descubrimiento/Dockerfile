# ─── Etapa 1: Compilación ────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# ─── Etapa 2: Ejecución ──────────────────────────────────────────────────────
FROM eclipse-temurin:24-jre
# curl es necesario para los health checks de docker-compose
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
