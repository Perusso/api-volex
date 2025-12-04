# 1. ESTÁGIO DE CONSTRUÇÃO (BUILD STAGE)
# Usamos uma imagem base leve que já tem o JDK instalado para compilar o projeto.
FROM eclipse-temurin:17-jdk-focal AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
# Copia o arquivo pom.xml para que o Maven baixe as dependências primeiro (otimiza o cache)
COPY pom.xml .

# Baixa as dependências. Se o pom.xml não mudar, esta etapa é cacheada.
RUN ./mvnw dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Empacota a aplicação em um JAR executável
RUN ./mvnw clean install -DskipTests

# 2. ESTÁGIO DE EXECUÇÃO (RUN STAGE)
# Usamos uma imagem base ainda mais leve (JRE) para a execução final.
# A imagem JRE é menor e mais segura para rodar em produção.
FROM eclipse-temurin:17-jre-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR do estágio de construção
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que o Spring Boot usa (8080)
EXPOSE 8080

# Comando para rodar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]