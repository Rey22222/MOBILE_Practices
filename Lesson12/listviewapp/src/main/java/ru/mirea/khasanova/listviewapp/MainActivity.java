package ru.mirea.khasanova.listviewapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.book_list_view);
        List<String> books = new ArrayList<>();
        books.add("Урсула Ле Гуин — Волшебник Земноморья");
        books.add("Урсула Ле Гуин — Гробницы Атуана");
        books.add("Урсула Ле Гуин — На последнем берегу");
        books.add("Диана Уинн Джонс — Ходячий замок");
        books.add("Диана Уинн Джонс — Воздушный замок");
        books.add("Диана Уинн Джонс — Дом с характером");
        books.add("Дж. Р. Р. Толкин — Хоббит, или Туда и обратно");
        books.add("Дж. Р. Р. Толкин — Братство Кольца");
        books.add("Дж. Р. Р. Толкин — Две крепости");
        books.add("Дж. Р. Р. Толкин — Возвращение короля");
        books.add("Джо Аберкромби — Кровь и железо");
        books.add("Джо Аберкромби — Прежде чем их повесят");
        books.add("Джо Аберкромби — Последний довод королей");
        books.add("Робин Хобб — Ученик убийцы");
        books.add("Робин Хобб — Королевский убийца");
        books.add("Робин Хобб — Странствия убийцы");
        books.add("Холли Блэк — Жестокий принц");
        books.add("Холли Блэк — Злой король");
        books.add("Холли Блэк — Королева ничего");
        books.add("Кассандра Клэр — Город костей");
        books.add("Кассандра Клэр — Город праха");
        books.add("Кассандра Клэр — Город стекла");
        books.add("Рик Риордан — Перси Джексон и Похититель молний");
        books.add("Рик Риордан — Перси Джексон и Море чудовищ");
        books.add("Рик Риордан — Перси Джексон и Проклятие титана");
        books.add("Рэнсом Риггз — Дом странных детей");
        books.add("Рэнсом Риггз — Город пустых");
        books.add("Рэнсом Риггз — Библиотека душ");
        books.add("Корнелия Функе — Чернильное сердце");
        books.add("Корнелия Функе — Чернильная кровь");
        books.add("Корнелия Функе — Чернильная смерть");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                books
        );

        listView.setAdapter(adapter);
    }
}