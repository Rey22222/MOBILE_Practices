# Практическая работа №12

**Тема:** Основные способы отображения списков в Android: ScrollView, ListView, RecyclerView. Реализация архитектурного паттерна MVVM.

---

## Цель работы
Изучение и практическое применение основных компонентов Android SDK, предназначенных для отображения списков данных: **ScrollView**, **ListView** и **RecyclerView**. Освоение современного подхода к построению пользовательского интерфейса с использованием архитектурного паттерна **MVVM** (на примере связки **ViewModel** и **LiveData**) для создания эффективных и масштабируемых приложений.

---

## 1. Реализация ScrollViewApp
В данном модуле был реализован простейший способ прокрутки контента, когда количество элементов заранее известно и невелико.
* **Разметка:** В файле `activity_main.xml` был создан корневой элемент **ScrollView**, содержащий **LinearLayout** с вертикальной ориентацией (контейнер `linear_layout_container`).
* **Логика:** В `MainActivity.java` в цикле от 0 до 99 программно создавались элементы списка. С помощью **LayoutInflater** для каждого элемента создавалось представление из отдельного XML-файла макета `item_simple_text.xml`.
* **Вычисления:** Для вычисления значений геометрической прогрессии со знаменателем 2 использовался класс **BigInteger**, так как стандартные типы данных не позволяют хранить числа такого порядка (до 2^99).
* **Результат:** Каждый созданный объект добавлялся в **LinearLayout** с помощью метода **addView()**.
<img width="2381" height="1252" alt="image" src="https://github.com/user-attachments/assets/c169a1eb-a0bc-4c34-a4c2-4e1d94d14876" />

---

## 2. Реализация ListViewApp
Для отображения структурированного списка из более чем 30 элементов был использован компонент **ListView**.
* **Данные:** Сформирован список из 31 книги в жанрах фэнтези и Young Adult. В список вошли произведения таких авторов, как **Урсула Ле Гуин**, **Дж. Р. Р. Толкин**, **Джо Аберкромби**, **Холли Блэк**, **Рик Риордан**, **Рэнсом Риггз**, **Корнелия Функе** и др.
* **Адаптер:** Использован стандартный **ArrayAdapter<String>**, который выступает мостом между массивом строк и визуальными элементами **ListView**.
* **Особенности:** Данный подход более производителен, чем ScrollView, так как **ListView** создает только те элементы, которые видны на экране.
<img width="2343" height="1252" alt="image" src="https://github.com/user-attachments/assets/5eaef05c-b4c0-46c3-94fe-5e37bcb5ff5b" />

---

## 3. Реализация RecyclerViewApp (Исторические события)
В этом модуле реализован современный стандарт работы со списками с использованием **RecyclerView** и архитектуры **MVVM**.
* **Модель:** Создан класс **HistoricalEvent.java** с полями для заголовка, описания и ID ресурса изображения.
* **ViewModel:** Создан класс **EventViewModel**, содержащий **MutableLiveData<List<HistoricalEvent>>**. Метод `loadEvents()` имитирует загрузку данных.
* **Адаптер:** Реализован **EventAdapter** с использованием паттерна **ViewHolder**, который кэширует ссылки на внутренние элементы разметки (**ImageView**, **TextView**), предотвращая лишние вызовы `findViewById`.
* **Связь:** **MainActivity** подписывается на изменения **LiveData** через метод `observe()`. При обновлении списка адаптер получает команду на перерисовку данных.
<img width="2382" height="1238" alt="image" src="https://github.com/user-attachments/assets/7b0191df-2c59-48a8-b2dc-357c289eb8ee" />

---

## 4. Реализация индивидуального проекта Succuforest

В основном проекте **Succuforest** (магазин суккулентов) реализована многомодульная архитектура и продвинутая работа со списками в парадигме **Clean Architecture**.

### Создание заглушки в Репозитории
В модуле **:data** реализован класс-заглушка **MockNetworkApi**, который имитирует получение данных из внешнего API. Этот класс содержит набор данных о растениях, включая их идентификаторы, названия, цены и ссылки на изображения в ресурсах.

```
public static class Dto {
    public int id;
    public String name;
    public String price;
    public String img;
    public String desc;

    public Dto(int id, String name, String price, String img, String desc) {
        this.id = id; 
        this.name = name; 
        this.price = price; 
        this.img = img; 
        this.desc = desc;
    }
}
```
<img width="2382" height="892" alt="image" src="https://github.com/user-attachments/assets/28cff1da-9f68-4cef-9927-ddc7f20d96aa" />

### Передача данных через ViewModel и LiveData
Для передачи данных из слоя данных в пользовательский интерфейс используется связка **ViewModel** и **LiveData**. Во ViewModel (например, **CatalogViewModel**) создается **MutableLiveData**, которая обновляется после получения данных из репозитория. Фрагмент подписывается на изменения этой **LiveData** и автоматически обновляет экран.

```java
public class CatalogViewModel extends ViewModel {
    private final MutableLiveData<List<Succulent>> succulents = new MutableLiveData<>();
    private final GetSucculentCatalogUseCase getCatalogUseCase;

    public CatalogViewModel(SucculentRepositoryImpl repository) {
        this.getCatalogUseCase = new GetSucculentCatalogUseCase(repository);
        loadCatalog();
    }

    public void loadCatalog() {
        // Данные передаются в LiveData
        List<Succulent> list = getCatalogUseCase.execute();
        succulents.setValue(list);
    }
    
    public LiveData<List<Succulent>> getSucculents() { 
        return succulents; 
    }
}
```
### Использование MediatorLiveData
Для главного экрана был применен **MediatorLiveData**, позволяющий объединять несколько источников данных (например, результаты поиска и результаты работы AI-модели) в один поток для отображения в интерфейсе.

### Отображение данных в RecyclerView
Данные, полученные через **LiveData**, отображаются в **RecyclerView** с помощью кастомного адаптера **SucculentAdapter**. В **CatalogFragment** производится настройка сетки (**GridLayoutManager**) и установка адаптера. Для эффективной загрузки изображений по их строковым именам из папки `drawable` интегрирована библиотека **Glide**.

```java
// Фрагмент кода из SucculentAdapter для привязки данных
@Override
public void onBindViewHolder(@NonNull VH holder, int position) {
    Succulent s = list.get(position);
    holder.name.setText(s.getName());
    holder.price.setText(s.getPrice());
    
    // Динамическое получение ID ресурса
    int resId = context.getResources().getIdentifier(s.getImageUrl(), "drawable", context.getPackageName());
    
    // Загрузка изображения через Glide с кэшированием
    Glide.with(context)
         .load(resId != 0 ? resId : android.R.drawable.ic_menu_gallery)
         .centerCrop()
         .into(holder.img);
}
```
<img width="2384" height="901" alt="image" src="https://github.com/user-attachments/assets/e9a89a14-c930-45de-ad01-9623c24f1ca6" />

## Итоги

В ходе выполнения практической работы были изучены и применены на практике три основных способа отображения списков в Android:

*   **ScrollView:** Является простейшим инструментом, подходящим только для небольших объемов статического контента. Его использование для динамических списков неэффективно и может привести к проблемам с производительностью.
*   **ListView:** Представляет собой классический механизм отображения списков, однако для обеспечения плавной работы требует ручной реализации паттерна **ViewHolder**. На данный момент считается устаревшим компонентом.
*   **RecyclerView:** Мощный, гибкий и производительный компонент, являющийся современным стандартом разработки. Он обеспечивает высокую скорость работы за счет принудительного использования паттерна **ViewHolder** и эффективной рециркуляции представлений.

Использование архитектурного паттерна **MVVM** со связкой **ViewModel** и **LiveData** позволило создать надежное и легко поддерживаемое приложение. Такой подход эффективно решает проблемы жизненного цикла Android-компонентов (например, сохранение данных при повороте экрана) и обеспечивает чистоту кода за счет четкого разделения ответственности между слоями.

---
**Выполнила:** Хасанова Рената  
**Группа:** БСБО-07-22
