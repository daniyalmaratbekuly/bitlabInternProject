# Указываем базовый образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки (например, jar-файл) в образ
COPY build/libs/*.jar app.jar

# Открываем порт для приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]