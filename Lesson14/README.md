# Практическая работа №14

**Тема:** Фрагменты. Жизненный цикл, навигация и способы обмена данными.

---

## Цель работы
Получение комплексных знаний и практических навыков по использованию компонента **Fragment** в Android-приложениях. В ходе работы были изучены:
* Жизненный цикл фрагментов и его связь с жизненным циклом Activity.
* Способы добавления фрагментов и управление ими с помощью **FragmentManager**.
* Построение навигации между экранами, реализованными в виде фрагментов.
* Современные подходы к организации обмена данными между фрагментами: **Bundle**, **ViewModel** с **LiveData** и **Fragment Result API**.

---

## 1. Создание базового фрагмента и передача данных через Bundle (FragmentApp)
В рамках модуля **FragmentApp** было создано приложение, состоящее из одной Activity и одного фрагмента. Задача заключалась в передаче номера студента по списку (№20) из Activity во фрагмент при его инициализации.

**Реализация:**
В `MainActivity` был создан объект `Bundle`, в который помещалось целочисленное значение. Этот объект передавался в качестве аргумента при добавлении фрагмента в `FragmentContainerView`.

```
if (savedInstanceState == null) {
    Bundle bundle = new Bundle();
    // Номер студента по списку
    bundle.putInt("my_number_student", 20);

    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container_view, BlankFragment.class, bundle)
            .commit();
}
```
**BlankFragment.java (фрагмент кода):**
В методе `onViewCreated` данные извлекались из аргументов и отображались в `TextView`.

```
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Bundle args = getArguments();
    if (args != null) {
        int number = args.getInt("my_number_student", 0);
        TextView textView = view.findViewById(R.id.textViewStudentNumber);
        textView.setText("Номер студента: " + number);
    }
}
```
<img width="2380" height="906" alt="image" src="https://github.com/user-attachments/assets/6e899900-6327-428b-8f26-df371c18f502" />

## 2. Навигация и обмен данными через ViewModel (FragmentManagerApp)

В данном модуле была реализована навигация между списком стран и экраном с их подробным описанием. Для обмена данными использовалась архитектура **Shared ViewModel**.

**Ключевые этапы:**
* Был создан класс модели **Country** для хранения названия и описания.
* Создана **SharedViewModel**, содержащая `MutableLiveData<Country>` для хранения состояния выбранного элемента.
* В `ListFragment` при нажатии на элемент списка объект передавался во ViewModel, а затем выполнялась транзакция `replace` с вызовом `addToBackStack(null)` для поддержки кнопки «Назад».

**SharedViewModel.java (фрагмент кода):**
```
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Country> selectedCountry = new MutableLiveData<>();

    public void selectCountry(Country country) {
        selectedCountry.setValue(country);
    }

    public LiveData<Country> getSelectedCountry() {
        return selectedCountry;
    }
}
```
**DetailsFragment.java (фрагмент кода):**

Фрагмент подписывается на изменения во ViewModel, что обеспечивает реактивное обновление UI.

```
viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
viewModel.getSelectedCountry().observe(getViewLifecycleOwner(), country -> {
    if (country != null) {
        nameTextView.setText(country.getName());
        descriptionTextView.setText(country.getDescription());
    }
});
```
<img width="2345" height="933" alt="image" src="https://github.com/user-attachments/assets/830ae7e6-9151-40d0-8741-13834b7f9abc" />
<img width="2378" height="904" alt="image" src="https://github.com/user-attachments/assets/b4f38d42-4b89-45e9-8053-095573317e36" />

## 3. Передача данных через Fragment Result API (ResultApiFragmentApp)

Реализована передача данных от основного фрагмента (**DataFragment**) к диалоговому (**BottomSheetFragment**) без использования ViewModel.

**Логика работы:**
* **Отправитель:** В `DataFragment` текст из поля ввода упаковывался в `Bundle` и отправлялся через метод `setFragmentResult`.
* **Получатель:** В `BottomSheetFragment` в методе `onCreate` был установлен слушатель `setFragmentResultListener`, который извлекал данные по ключу `requestKey`.

**DataFragment.java (фрагмент кода):**

```
button.setOnClickListener(v -> {
    String text = editText.getText().toString();
    Bundle result = new Bundle();
    result.putString("bundleKey", text);
    getParentFragmentManager().setFragmentResult("requestKey", result);

    new BottomSheetFragment().show(getParentFragmentManager(), "ModalBottomSheet");
});
```
<img width="2396" height="901" alt="image" src="https://github.com/user-attachments/assets/a16dd721-1509-4ab5-ae01-77e747a1de1a" />

## 4. Контрольное задание: Проект Succuforest

### Реализация экрана Профиль (Архитектура MVVM)

В основном проекте **Succuforest** был реализован экран профиля пользователя, интегрированный в общую систему навигации приложения.

#### ViewModel (ProfileViewModel.java):
* Отвечает за получение актуальных данных о текущем пользователе из **Firebase Auth**.
* Содержит **LiveData<String>** для передачи email пользователя во фрагмент.
* Реализует событие **logoutEvent** для оповещения фрагмента о необходимости завершения сессии и выхода из системы.

#### View (ProfileFragment.java):
* Подписывается на данные из **ProfileViewModel** и динамически обновляет текстовые поля на экране.
* При срабатывании события выхода выполняет переход на **LoginActivity**, используя флаги **FLAG_ACTIVITY_CLEAR_TASK** и **FLAG_ACTIVITY_NEW_TASK** для полной очистки стека экранов.
* Верстка фрагмента реализована на базе **ConstraintLayout** для обеспечения гибкого расположения элементов интерфейса.

#### Навигация (MainActivity.java):
Обработчик **BottomNavigationView** был расширен для поддержки нового раздела. Переключение между экранами «Главная», «Каталог» и «Профиль» осуществляется через метод **replace()** без добавления транзакции в бэк-стек, что соответствует стандартам проектирования нижнего меню навигации.
<img width="2335" height="1006" alt="image" src="https://github.com/user-attachments/assets/04686ddc-f603-4ae8-a774-71e61bf475d5" />

---

## Итоги

В ходе выполнения работы были освоены три ключевых механизма обмена данными в Android:
* **Bundle:** Идеален для передачи первоначальных параметров при создании экземпляра фрагмента.
* **ViewModel и LiveData:** Мощный инструмент для организации общего состояния между несколькими фрагментами, обеспечивающий сохранение данных при смене конфигурации устройства.
* **Fragment Result API:** Оптимальный выбор для передачи разовых событий между компонентами, не имеющими прямой связи.

Приложение **Succuforest** теперь обладает современной модульной структурой, где каждый экран является независимым фрагментом, а навигация управляется централизованно через **FragmentManager**.

---
Выполнила: Хасанова Рената  
Группа: БСБО-07-22
