# Etapa 1: Compilación de la aplicación
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia de dependencias y código fuente
COPY pom.xml .
COPY src ./src

# Compilación sin ejecutar pruebas para acelerar el despliegue
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia del archivo JAR generado
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente la variable PORT en producción
ENV PORT=8080
EXPOSE ${PORT}

# Comando de inicio asignando el puerto dinámico de Render
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]