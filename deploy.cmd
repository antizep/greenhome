@echo off
echo --- 📦 Сборка JAR (Maven) ---
call mvn clean package
if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%

echo --- 🐳 Сборка и деплой Docker ---
docker compose up -d --build

echo --- ✅ Готово! ---