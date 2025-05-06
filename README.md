# WebRise

REST‑микросервис для управления пользователями и их подписками на цифровые сервисы (Netflix, YouTube Premium, Spotify и т.д.)

## Стек

| Технология        | Версия | Назначение                |
| ----------------- | ------ | ------------------------- |
| Java              | 17     | JDK / язык                |
| Spring Boot       | 3.4.x  | Web, Data JPA, Scheduling |
| PostgreSQL        | 16     | Хранилище                 |
| Docker / Compose  | v2     | Локальный запуск + CI     |
| springdoc‑openapi | 2.5    | Swagger UI / OpenAPI 3    |
| Lombok            | 1.18   | boilerplate‑free код      |

---

## Быстрый старт (Docker)

```bash
# 1. Клонируйте репозиторий
$ git clone https://github.com/your-org/webrise.git && cd webrise

# 2. Соберите и запустите сервис + Postgres
$ docker compose up --build
```

* API будет доступно по адресу [**http://localhost:8080**](http://localhost:8080)
* Swagger UI: [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

---

## Локальный запуск без Docker

1. **Пред‑реквизиты**: JDK 17, Maven 3.9+, Postgres 16 (или любой 14+).
2. Создайте пользователя и БД:

   ```bash
   psql -U postgres -c "CREATE DATABASE webrise;"
   ```
3. Активируйте профиль *local* и запустите:

   ```bash
   export SPRING_PROFILES_ACTIVE=local
   ./mvnw spring-boot:run
   ```

> Вместо установки Postgres можно запустить только БД:
> `docker compose up -d postgres`

---

## Конфигурация

| Профиль                           | Описание                           | Файл                    |
| --------------------------------- | ---------------------------------- | ----------------------- |
| `docker` (по умолчанию в compose) | Поднимается с контейнером Postgres | `application.yml`       |
| `local`                           | Для запуска на хост‑машине         | `application-local.yml` |

Настройки можно переопределить переменными окружения: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

---

## Документация API

Добавлена зависимость **springdoc-openapi-starter-webmvc-ui**; UI доступен по адресу `/swagger-ui.html`, JSON‑спецификация — `/v3/api-docs`.

Пример запроса:

```bash
curl -X POST http://localhost:8080/users \
     -H "Content-Type: application/json" \
     -d '{"email":"alice@example.com","fullName":"Alice"}'
```

Полный набор эндпоинтов, схем и возможных ответов смотрите в Swagger.
