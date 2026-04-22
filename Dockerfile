FROM bellsoft/liberica-openjdk-alpine:21

# Копируем наш jar внутрь
COPY target/greenhouse-1.0.0.jar app.jar

# Запускаем
ENTRYPOINT ["java", "-jar", "/app.jar"]