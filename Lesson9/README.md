# Практическая работа №9

**Тема:** Проектирование приложения в парадигме Clean Architecture. Use-case диаграмма, декомпозиция на слои (presentation/domain/data) и реализация каркаса приложения.

---

## Цель работы
Закрепить навыки:
* проектирования сценариев использования (Use Case) и выделения акторов;
* декомпозиции мобильного приложения на слои **presentation / domain / data**;
* применения принципа **Dependency Rule** (Правило зависимостей) и инверсии зависимостей;
* реализации каркаса приложения с use-case классами и репозиториями;
* сохранения данных через `SharedPreferences` с соблюдением архитектурной изоляции.

---

## Проектирование приложения (Succuforest)

### USE-CASE диаграмма
Для интернет-магазина суккулентов **Succuforest** были выделены два основных актора:
1. **Гость** — имеет доступ к базовому функционалу: просмотр каталога растений, поиск конкретного суккулента по ID, использование функции определения вида растения (AI) и инициация входа в систему.
2. **Авторизованный пользователь** — наследует права гостя, но дополнительно может добавлять товары в корзину/избранное, оформлять заказы и сохранять историю покупок.

Ключевые сценарии использования (Use Cases), реализованные в каркасе:
* **Просмотр каталога:** Получение списка доступных суккулентов.
* **Просмотр товара:** Получение детальной информации (название, цена) по ID.
* **Авторизация:** Имитация входа пользователя (получение токена).
* **Определение типа (ML):** Имитация работы нейросети для распознавания вида суккулента.

Операция «Сохранение данных» проектируется как *Include* для сценариев, изменяющих состояние.

![Use-Case диаграмма](https://plantuml.online/png/dLPTJnDH47tdLzpK1o0HYA-6a1H4Wp6QQ8mllAnbXSQwIxPRCL4Je22Q4a3aLJxy1MlRbQNG-XVc_YFFpDrTjWkD2gHDzzuxP-QSENFROJKsDgReEmLwJQ_hLUhH6NKeKjJJ6tYjqWMTKqXTYlIk6g4V-7o1fm1FUbqUWb766Ag-BZlBbcUzKI-jqkj5pwqv2pDknVNKhJkp_9yvKQlQ9Qjgg_T32d_J0yxBdkijs9vPVx5KzcrPlUywVdwjw7dkIdRnOoQXLVAn_lu_8GSLbaNsx99lEOiL-x_HysjzzJAP6F7lSKF3fejxr45Fq3JTa9OOrWNwXZNkxdP1MLNre6PN_NHlZvecXY8wHVUR5A2zNTsWCqM_iN2kjvCDQVXv3FEyQdkSsNX7JD9EuoCA4GtGW9fmpT7rE4hNuP-GGhskKCOQXRULtk9o53imBNjqI6fMan_0qkIzbBtmh5OgrLwv7fyeBYyN89le58koZtnC3cK1gm5a9CeHI-mDR3K3OrQDfHb5tXA_5kY8HmG05tZdSGcvFeM9OL81LEXHQzuPaHISg4STfTTKBEvFKUHNCaz0YKP5p1dBjohkuhydMvMcRQ7XTU13nmv_DAFAbGZgFTktofLg7bMurn4S6JmMeuLN6rWKpJjITbEoDMGtDW98D7KTxB3PnVPdQVIPUlZioMD3uuLj-KjeJt_2Vei5-XDpdvXtw1ZLjbWcOHTl252nvYzT9_--RkXDBbKKLH2p8tBKCwmVkOjkpSy57h3wxCQ4TDFqn7G4XiT-AE7thRUvs4CkC1kBRj0f9gGf0K_jjsLx9HTp0hnJ34J04HFSEtwG-OpabcsQBQZG4kuRAAUJ4JecynHKoewTGp-MaVijERe6I75vrERkNNM3wQ9Sw6Y4KNZkKbyCyI6K-2BTr3i0xI3o3BurhOPGkZvwbQ7UZDMBTMcdTqSe0d-b0-F3wRw9F84VTf4a0lpbZASJPsOT1xD3aIvcFPqkPdsNBeg71kMzoRLtMIBVGPmYOxLaATkmP0rQP-gOVUVRpW9ga2vYYYAnXxaqzGPx6Tt3B21xL-wK83Vpk3NIKj2xepZ4M2l-RZOpXRevbA-WoKbR4azDYO6LkAulFSBOWZmcfnGAUgoscXmVvy5GTpCmLmwOGUW_a-4zvvGgjGKRnFTHqxdU0PM4jjoDkt8xn6BBHBRuxcaRBsA31t3tHjJB9dV2tN3aswt3SvDK4yZlcHvTwBhvfWNZyV4f-5T8fI2o6RCTl6-CCFX0SUxwFNoX_m40)

### Разделение на слои
Архитектура приложения строго разделена на три слоя согласно принципам Clean Architecture:

1. **Presentation Layer:** Отвечает за взаимодействие с пользователем. Включает `MainActivity` (с обновленным дизайном в зеленых тонах, карточками и полями ввода), которая вызывает UseCase'ы и отображает результат.
2. **Domain Layer:** Ядро бизнес-логики. Содержит:
    * *Use Cases:* `LoginUseCase`, `GetSucculentCatalogUseCase`, `GetSucculentDetailsUseCase`, `DetermineSucculentTypeUseCase`.
    * *Models:* Сущность `Succulent`.
    * *Repository Interfaces:* Контракт `SucculentRepository`.
3. **Data Layer:** Отвечает за работу с данными. Содержит реализацию `SucculentRepositoryImpl`, которая на данном этапе использует заглушки (Stubs) и тестовые списки (Эхеверия, Алоэ, Хавортия) вместо реальной БД.

![Скриншот структуры проекта](https://radika1.link/2025/12/29/image2d4afd4f1de2e56b.png)

---

## Реализация каркаса приложения

### Часть 1. Учебный проект (MovieProject)
В рамках практического задания был реализован базовый проект для работы с любимыми фильмами. Каркас построен на принципах чистой архитектуры:
* Слой **Domain** определяет интерфейс репозитория и бизнес-логику (сохранение и получение названия фильма), оставаясь полностью независимым от Android-библиотек.
* Слой **Data** реализует этот интерфейс, используя `SharedPreferences` для физического хранения данных. Зависимость направлена внутрь (Data зависит от Domain).
* Слой **Presentation** использует UseCase классы для связи UI и данных. Кнопки на экране инициируют сохранение текста из поля ввода или его чтение, при этом Activity не знает о деталях реализации.

![Ввод данных](https://s2.radikal.cloud/2025/12/29/imagee563b4630681c614.png)
![Сохранение данных](https://s3.radikal.cloud/2025/12/29/image5519186138f56c57.png)
![Просмотр данных](https://s2.radikal.cloud/2025/12/29/image1925821c0116a7be.png)

### Часть 2. Индивидуальный проект (Succuforest)
Был создан проект `ru.mirea.khasanova.succuforest`, служащий каркасом для будущего интернет-магазина.

* **Структура:** Проект сразу разбит на пакеты `domain`, `data`, `presentation`.
* **Domain:** Реализованы 4 сценария использования (Каталог, Детали, Вход, AI).
* **Data:** Создан репозиторий с тестовыми данными. Реализован поиск по ID и имитация ответа сервера при авторизации.
* **Presentation:** Реализован UI с использованием стиля Material Design (зеленая цветовая гамма). Добавлена логика обработки ввода ID пользователя и отображения результатов в `CardView`.

Приложение успешно собирается, все слои изолированы. Use-case вызываются из Activity, проходят через абстракцию репозитория и возвращают данные из Data-слоя обратно в UI.

![Вход](https://s3.radikal.cloud/2025/12/29/image769fb9dad36dc48b.png)
![Каталог](https://radika1.link/2025/12/29/image04869919c0066d90.png)
![Пример поиска по ID](https://s3.radikal.cloud/2025/12/29/imageab7dd87b209658c8.png)
![Определение суккулента по фото](https://s2.radikal.cloud/2025/12/29/image1a1f463816a92ca1.png)


## Итоги
В ходе выполнения работы спроектирован и реализован архитектурный каркас мобильного приложения **Succuforest** в парадигме Clean Architecture.

1. Определены акторы и варианты использования.
2. Произведена декомпозиция на слои, соблюдено правило зависимостей (**Dependency Rule**).
3. Реализованы основные сущности и сценарии использования (Use Cases).
4. Созданы заглушки репозиториев, позволяющие тестировать бизнес-логику и UI без подключения реального бэкенда.
5. Продемонстрирована работа приложения: просмотр каталога, поиск по ID, имитация входа и ML-анализа.

Проект готов к дальнейшему масштабированию (подключению Room, Retrofit) без необходимости изменения кода в слое Domain.

---

**Выполнила:** Хасанова Рената  
**Группа:** БСБО-07-22
