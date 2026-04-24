#!/bin/bash

# Остановить выполнение при любой ошибке
set -e

echo "--- 📦 Сборка JAR (Maven) ---"
./mvnw clean package -DskipTests

echo "--- 🐳 Сборка и деплой Docker ---"
# Используем контекст, если он задан в окружении, иначе локально
docker compose up -d --build

echo "--- ✅ Готово! ---"