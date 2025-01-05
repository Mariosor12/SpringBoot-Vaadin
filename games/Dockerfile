# Usa la imagen de Gradle como base para construir el proyecto  
FROM gradle:7.6.0-jdk17 AS builder  

# Establece el directorio de trabajo en /games  
WORKDIR /games  

# Copia los archivos de configuración necesarios para Gradle al contenedor  
COPY settings.gradle .  
COPY build.gradle .  
COPY gradlew .  
COPY gradle ./gradle   
COPY src ./src  

# Verifica que el wrapper de Gradle sea ejecutable (opcional)  
RUN chmod +x ./gradlew  

# Ejecuta la construcción del proyecto, incluyendo el frontend  
RUN ./gradlew clean build --no-daemon  

# Usa una imagen de OpenJDK para crear la imagen final  
FROM openjdk:17-slim  

# Establece el directorio de trabajo en /games  
WORKDIR /games  

# Copia el archivo JAR construido desde la fase de construcción  
COPY --from=builder /games/build/libs/games-0.0.1-SNAPSHOT.jar /games/app.jar  

# Expone el puerto en el que se ejecutará la aplicación  
EXPOSE 8080  

# Comando para ejecutar la aplicación en el contenedor  
CMD ["java", "-jar", "/games/app.jar"]