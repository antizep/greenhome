# 🌿 GreenHome Project Backlog

## 🚀 High Priority
- [ ] **Тестирование и покрытие:** Прикрутить **Jacoco** (цель: >80% покрытия).
- [ ] **Изоляция окружения:** Перейти с H2 на **Testcontainers (PostgreSQL)** для "честных" тестов.
- [ ] **Обработка исключений:** Реализовать `@ControllerAdvice` для чистого API (без StackTrace в ответах).

## 🛠 Технический долг и рефакторинг
- [ ] **Валидация данных:** Добавить `@Validated` и аннотации `javax.validation` (NotNull, Min/Max) в DTO.
- [ ] **Security:** Добавить базовую авторизацию для доступа к управлению теплицей.

## 🛠 Process & Workflow
- [ ] Перейти на **GitHub Flow**: прекратить коммиты напрямую в `master`, внедрить разработку через `feature/` ветки и Pull Requests.
- [ ] Внедрить **Conventional Commits**: использовать понятные префиксы в сообщениях коммитов (`feat:`, `fix:`, `refactor:`, `docs:`).


## ✨ Новые фичи (Roadmap)


## 📝 Заметки / Отменено