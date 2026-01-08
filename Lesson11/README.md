# Практическая работа №11

**Тема:** Проектирование и реализация архитектурного шаблона MVVM в мобильном приложении. Использование LiveData и MediatorLiveData для управления состоянием интерфейса.

---

## Цель работы
*   Перенос логики взаимодействия со слоем domain из Activity во ViewModel.
*   Организация реактивного обновления пользовательского интерфейса через механизмы LiveData.
*   Изучение и внедрение MediatorLiveData для объединения нескольких источников данных.
*   Обеспечение стабильности данных при изменении конфигурации устройства (повороте экрана).

---

## Часть 1: Учебный проект (Movie Project)

В рамках учебного проекта была произведена декомпозиция класса MainActivity. Вся логика, отвечающая за вызов сценариев использования (Use Cases), была перенесена в специально созданный класс MainViewModel. 

1) MainViewModel
```
package ru.mirea.khasanova.Lesson11.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.khasanova.Lesson11.domain.model.Movie;
import ru.mirea.khasanova.Lesson11.domain.repository.MovieRepository;
import ru.mirea.khasanova.Lesson11.domain.usecase.GetFavoriteMovieUseCase;
import ru.mirea.khasanova.Lesson11.domain.usecase.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private MutableLiveData<String> resultLive = new MutableLiveData<>();

    public LiveData<String> getResultLive() {
        return resultLive;
    }

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(String movieName) {
        Movie movie = new Movie(2, movieName);
        boolean success = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        if (success) {
            resultLive.setValue("Фильм сохранён!");
        } else {
            resultLive.setValue("Ошибка: Название пустое");
        }
    }

    public void load() {
        Movie movie = new GetFavoriteMovieUseCase(movieRepository).execute();
        resultLive.setValue("Избранный фильм: " + movie.getName());
    }
}
```

2) MainViewModelFactory

```
package ru.mirea.khasanova.Lesson11.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.khasanova.Lesson11.data.repository.MovieRepositoryImpl;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public MainViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieRepositoryImpl movieRepository = new MovieRepositoryImpl(context);
        return (T) new MainViewModel(movieRepository);
    }
}
```

3) MainActivity

```
package ru.mirea.khasanova.Lesson11.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.khasanova.Lesson11.R;


public class MainActivity extends AppCompatActivity {

    private EditText editTextMovie;
    private TextView textViewResult;
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        textViewResult = findViewById(R.id.textViewResult);

        vm = new ViewModelProvider(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);
        vm.getResultLive().observe(this, text -> {
            textViewResult.setText(text);
        });
        Button buttonSave = findViewById(R.id.buttonSaveMovie);
        buttonSave.setOnClickListener(v -> {
            String movieName = editTextMovie.getText().toString();
            vm.save(movieName);
        });
        Button buttonGet = findViewById(R.id.buttonGetMovie);
        buttonGet.setOnClickListener(v -> {
            vm.load();
        });
    }
}
```

Для корректного создания экземпляра ViewModel с передачей в него репозитория была реализована фабрика ViewModelFactory. Это позволило изолировать слой представления от деталей создания зависимостей. В самой активности была настроена подписка на LiveData, что позволило интерфейсу автоматически реагировать на изменения данных в репозитории.
<img width="451" height="1001" alt="image" src="https://github.com/user-attachments/assets/f713dd61-c718-4306-b5c4-0b7c73a491f6" />
<img width="453" height="994" alt="image" src="https://github.com/user-attachments/assets/b59b022e-6112-4d32-8c9e-c0ca894e03c8" />
<img width="754" height="335" alt="image" src="https://github.com/user-attachments/assets/f06aeac8-75c9-4b1e-b812-cbccc7881064" />

---

## Часть 2: Личный проект (Succuforest)

В проекте Succuforest архитектурный паттерн MVVM был внедрен для всех ключевых экранов приложения. Это позволило сделать каждый модуль независимым и устойчивым к жизненному циклу Android.

### 1. Главная страница и Поиск
Для главного экрана была реализована HomeViewModel. Основной особенностью стало использование MediatorLiveData. Этот инструмент позволил объединить два независимых потока событий: результат ручного поиска растения по идентификатору и результат работы имитационной модели анализа изображений. При запуске приложения во ViewModel задается начальное состояние, которое информирует пользователя о возможностях системы, исключая отображение пустого экрана.

### 2. Каталог растений
В CatalogViewModel была реализована логика автоматической загрузки списка суккулентов при создании экрана. Данные хранятся в объекте MutableLiveData. Фрагмент каталога лишь наблюдает за этим списком и передает его в адаптер для отрисовки. Это гарантирует, что при повороте устройства список не будет запрашиваться повторно, а просто отобразится из памяти ViewModel.

### 3. Избранное
Для экрана избранных товаров реализована FavoritesViewModel. Она обеспечивает актуальность списка растений, которые пользователь отметил как понравившиеся. При каждом возвращении пользователя на этот экран данные обновляются через вызов метода репозитория, что гарантирует синхронизацию с локальной базой данных Room.

### 4. Карточка растения (Детали)
DetailsViewModel управляет состоянием экрана подробной информации. Она отвечает за загрузку данных о конкретном суккуленте по его идентификатору и обработку нажатия кнопки добавления в избранное. Изменение статуса избранного происходит во ViewModel, после чего обновленная сущность передается обратно в интерфейс через LiveData.

---

## Стратегии обработки данных

В приложении Succuforest сохранена и адаптирована под MVVM работа с тремя типами источников:
*   **SharedPreferences:** Используется для хранения данных профиля и сессии пользователя. Доступ к ним осуществляется через репозиторий, а результат отображается во ViewModel.
*   **Room:** Выступает в роли локального кэша. ViewModel получает данные из базы, что позволяет приложению работать без доступа к сети.
*   **Mock Network API:** Имитирует внешние запросы. Репозиторий получает данные, сохраняет их в Room, а ViewModel транслирует их в интерфейс.
### Реализация MediatorLiveData на примере каталога
Преимущества MediatorLiveData:
1) Автоматическое обновление при изменении любого источника
2) Изоляция логики комбинирования данных
3) Реактивное обновление UI
<img width="2279" height="1304" alt="image" src="https://github.com/user-attachments/assets/d6fdb4bf-3e3f-4806-b8b2-ca7078811c1f" />

---

## Результаты выполнения
1.  **Архитектурная целостность:** Достигнуто полное разделение ответственности между View и ViewModel.
2.  **Реактивность:** Интерфейс обновляется автоматически при изменении данных в LiveData, что сокращает количество кода в Activity и Fragment.
3.  **Надежность:** Приложение корректно обрабатывает повороты экрана, сохраняя все введенные данные и результаты поиска.
4.  **Сложные состояния:** С помощью MediatorLiveData реализована логика объединения нескольких источников данных в один поток для упрощения работы UI.

---
Выполнила: Хасанова Рената  
Группа: БСБО-07-22
