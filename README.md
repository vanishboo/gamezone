<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&height=250&color=0:2B2D42,50:4A4E69,100:22223B&text=GameZone&fontColor=FFFFFF&fontSize=64&desc=Система%20бронирования%20игровых%20мест&descSize=20&descAlignY=67" alt="GameZone banner" />

[![Java](https://img.shields.io/badge/Java-23-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Jakarta Servlet](https://img.shields.io/badge/Jakarta%20Servlet-6.0-0E6BA8?style=for-the-badge)](https://jakarta.ee/specifications/servlet/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-WAR-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

<img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=600&pause=1000&color=7C83FD&center=true&vCenter=true&width=700&lines=GameZone+%E2%80%94+бронь+ПК+и+консолей+в+пару+кликов;Автоподбор+свободных+мест+для+команды;Управление+бронированиями+и+личным+кабинетом" alt="typing animation" />

</div>

---

## О проекте

**GameZone** — это Java web-приложение для управления бронированием игровых мест в компьютерном клубе.

Что умеет система:
- 🔐 Регистрация, логин и защищённые маршруты.
- 🖥️ Каталог комнат и компьютеров с ценами.
- 📅 Пошаговое индивидуальное бронирование.
- 🤖 Автоматический подбор нескольких свободных ПК под команду.
- 🧾 Просмотр, фильтрация, изменение и отмена бронирований.
- 👤 Личный профиль пользователя и счётчик активных броней.

---

## ✨ Основные фичи

### 1) Авторизация и безопасность
- `AuthFilter` пропускает только публичные страницы без сессии и перенаправляет неавторизованных пользователей на `/login`.
- Пароли хешируются через **BCrypt**.

### 2) Бронирование в 4 шага
Поток `/booking` разбит на шаги:
1. Выбор даты.
2. Выбор свободного времени.
3. Выбор длительности.
4. Подтверждение с подсчётом стоимости.

### 3) Автобронирование для команды
Поток `/auto-booking` + `/auto-confirm`:
- выбирается комната, дата, время, длительность и количество игроков;
- сервис находит нужное число свободных компьютеров;
- после подтверждения создаётся сразу несколько броней.

### 4) Управление бронями
В разделе `my-bookings`:
- фильтрация по статусу через AJAX;
- редактирование времени/длительности;
- отмена или удаление брони.

---

## 🧱 Технологический стек

- **Backend:** Java 23, Jakarta Servlet API 6, Freemarker
- **Data layer:** JDBC DAO, PostgreSQL, HikariCP
- **Security:** (BCryptPasswordEncoder)
- **Build:** Maven, packaging `war`
- **Frontend:** Freemarker templates + CSS + Vanilla JS

---

## 🏗️ Архитектура

Слои приложения:
- **Controller** (`controller/*`) — HTTP-роутинг и работа с запросом/ответом.
- **Service** (`service/*`) — бизнес-логика бронирования и аккаунтов.
- **DAO** (`dao/*`) — SQL-запросы к PostgreSQL.
- **Model** (`model/*`) — доменные сущности (`Account`, `Booking`, `Computer`, `Room`, `Game`).
- **Infrastructure** (`listener`, `filter`, `util`) — инициализация контекста, авторизация, DB pool.

Инициализация зависимостей выполняется в `InitListener`, где создаются DAO/Service и кладутся в `ServletContext`.

---

## 🗺️ Основные маршруты

| Маршрут | Назначение |
|---|---|
| `/` | Лендинг/главная страница |
| `/register`, `/login`, `/logout` | Регистрация, вход, выход |
| `/home`, `/profile` | Личный кабинет и профиль |
| `/rooms`, `/computers` | Каталог залов и игровых мест |
| `/booking` | Пошаговое создание брони |
| `/auto-booking`, `/auto-confirm` | Автоподбор и подтверждение группы |
| `/my-bookings`, `/bookings-ajax` | История и фильтрация броней |
| `/edit-booking`, `/booking-actions` | Редактирование/действия над бронями |

---
