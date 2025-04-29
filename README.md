# Recipe Book

Простое fullstack-приложение "Recipe Book" с фронтендом на **Angular**, бэкендом на **Spring Boot** и базой данных **PostgreSQL**.  
Проект полностью готов для локального запуска через **Docker Compose**.

## Структура проекта

```
recipe-book/
├── front/              # Фронтенд (Angular)
│   └── Dockerfile      # Dockerfile для фронта
│
├── back/               # Бэкенд (Spring Boot)
│   └── Dockerfile      # Dockerfile для бэка
│
├── init.sql            # SQL-скрипт инициализации базы данных
├── docker-compose.yml  # Конфигурация Docker Compose
└── README.md           # Описание проекта
```

## Требования

- Установленные [Docker](https://www.docker.com/) и [Docker Compose](https://docs.docker.com/compose/).

## Как запустить проект

1. Клонировать репозиторий:

```bash
git clone https://github.com/your-username/recipe-book.git
cd recipe-book
```

2. Собрать и запустить все сервисы:

```bash
docker-compose up --build
```

3. Открыть приложение в браузере:

[http://localhost:18081/](http://localhost:18081/)

## Сервисы

| Сервис     | Назначение         | Порт               |
|------------|--------------------|--------------------|
| **frontend** | Angular-приложение | `localhost:18081`  |
| **backend**  | Spring Boot API    | `localhost:18080`  |
| **db**       | PostgreSQL база    | `localhost:15432`  |

## Данные для подключения к базе данных

- **База данных**: `your_database`
- **Пользователь**: `your_user`
- **Пароль**: `your_password`
- **Хост**: `localhost`
- **Порт**: `15432`

Можно подключаться через любой клиент базы данных, например, [DBeaver](https://dbeaver.io/).

## Команды

- Остановить сервисы и удалить тома базы данных:

```bash
docker-compose down -v
```

- Перезапустить проект с пересборкой:

```bash
docker-compose up --build
```

---

## Примечания

- База данных автоматически инициализируется скриптом `init.sql`.
- Фронтенд использует nginx для проксирования запросов к API.
- Проект предназначен для локальной разработки и тестирования.

---