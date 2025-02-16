# Usa a imagem do Eclipse Temurin com JDK 21
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia arquivos do Maven e torna o wrapper executável
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Baixa as dependências do Maven
RUN ./mvnw dependency:go-offline -B

# Expõe as portas para o Spring Boot e debug
EXPOSE 8080
EXPOSE 5005

# Comando para rodar o Spring Boot (hot reload ativado)
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
