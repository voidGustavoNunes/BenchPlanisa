# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiar arquivos do Maven e torná-lo executável
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Cache das dependências do Maven para builds mais rápidos
RUN ./mvnw dependency:go-offline -B

# Copiar o código-fonte
COPY src/ src/

# Compilar o projeto
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
