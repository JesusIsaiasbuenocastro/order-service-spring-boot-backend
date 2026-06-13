# ═══════════════════════════════════════════════════════════════════════════════
# Dockerfile — Order Service Spring Boot
#
# Usa multi-stage build: dos etapas en un solo archivo.
# ETAPA 1 (build):  compila el JAR usando Maven — imagen pesada, temporal
# ETAPA 2 (runtime): solo copia el JAR a una imagen mínima — imagen final liviana
#
# Resultado: imagen de ~200MB en lugar de ~600MB con Maven incluido
# ═══════════════════════════════════════════════════════════════════════════════

# ── ETAPA 1: Compilación ──────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos pom.xml PRIMERO y descargamos dependencias por separado.
# Docker cachea cada capa. Si solo cambias código fuente (no el pom),
# esta capa ya estará cacheada y el build será mucho más rápido.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# ── ETAPA 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Creamos un usuario no-root para correr la app (buena práctica de seguridad)
RUN groupadd -r appgroup && useradd -r -g appgroup appuser
USER appuser

# Copiamos SOLO el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto que expone el contenedor (debe coincidir con server.port)
EXPOSE 8080

# Flags de JVM recomendados para contenedores:
# -XX:+UseContainerSupport    → JVM detecta límites de memoria del contenedor (no del host)
# -XX:MaxRAMPercentage=75.0   → Usa máximo 75% de la RAM disponible para el heap
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "app.jar"]
