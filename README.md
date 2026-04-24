# Greenhouse Monitor & Control System 🌿

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff)

Enterprise-решение для автоматизации теплицы на базе **Orange Pi Zero 2W** и **Arduino Mega 2560**. Проект реализует полный цикл сбора данных с датчиков (температура, влажность почвы в 4 зонах) и управление исполнительными устройствами.

## 🚀 Ключевые особенности
- **TDD (Test Driven Development):** Бизнес-логика (парсинг, управление поливом) покрыта Unit-тестами с использованием JUnit 5 и Mockito.
- **Database Migrations:** Управление схемой PostgreSQL через **Liquibase**. Никаких ручных правок БД.
- **Robust Hardware Communication:** Реализован паттерн **Gateway** с Retry Policy для стабильной связи по Serial-порту (jSerialComm).
- **Enterprise Build Pipeline:** 
  - Сборка образа на Orange Pi с автоматическим версионированием (Timestamp).
  - Платформонезависимый скрипт деплоя (`deploy.sh`|`deploy.cmd`).
  - Разделение профилей `dev` (заглушки) и `prod` (реальное железо).

## 🏗 Архитектура системы
Проект разделен на логические слои:
1. **Controller Layer:** REST API (OpenAPI 3 / Swagger) для внешних систем.
2. **Service Layer:** Бизнес-логика и координация команд.
3. **Gateway Layer:** Управление коммуникацией с Arduino.
4. **Transport Layer:** Абстракция над Serial-портом для легкого тестирования и замены.

## 📦 Быстрый старт

### Требования
- Docker & Docker Compose
- Java 21 (для локальной разработки)

### Развертывание
Сборка и запуск всей системы (App + DB) одной командой:
```deploy.cmd
```

### Документация API
После запуска Swagger UI доступен по адресу:
`http://<orange-pi-ip>:8080/swagger-ui.html`
*Там же можно увидеть версию и точное время сборки текущего билда.*

## ⚙️ Конфигурация (application.properties)
- `arduino.port`: Путь к порту (по умолчанию `/dev/ttyACM0`).
- `spring.profiles.active`: Переключение между реальным железом (`prod`) и заглушками (`dev`).

## 📊 Схема данных
- `sensor_readings`: История показаний (температура воздуха/почвы, влажность 4-х зон).
- `watering_logs`: Журнал событий полива (Audit Trail) для отслеживания работы насосов.

---
**Разработано с акцентом на Clean Code и масштабируемость.**


## 🛠 Обслуживание и отладка (Troubleshooting)

### Просмотр логов
Для анализа работы системы (старт Spring Boot, запросы к БД, обмен данными с Arduino) используйте команды:

*   **Логи приложения:** 
    `docker compose logs -f app`
*   **Логи базы данных:** 
    `docker compose logs -f db`
*   **Проверка статуса контейнеров:** 
    `docker ps`

### Подключение к базе данных
Вы можете подключиться к PostgreSQL (например, через DBeaver) для ручного аудита:
- **Host:** `<ip-платы>`
- **Port:** `5432`
- **User/Pass:** `admin` / `secret` (по умолчанию)
