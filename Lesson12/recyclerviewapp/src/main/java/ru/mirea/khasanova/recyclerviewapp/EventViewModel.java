package ru.mirea.khasanova.recyclerviewapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {
    private final MutableLiveData<List<HistorycalEvent>> eventsLiveData = new MutableLiveData<>();

    public LiveData<List<HistorycalEvent>> getEvents() {
        return eventsLiveData;
    }

    public void loadEvents() {
        List<HistorycalEvent> events = new ArrayList<>();

        events.add(new HistorycalEvent(
                "Открытие Америки",
                "Экспедиция Христофора Колумба достигла берегов Нового Света в 1492 году.",
                R.drawable.event_america_discovery));

        events.add(new HistorycalEvent(
                "Куликовская битва",
                "Сражение русских полков во главе с Дмитрием Донским против войск Мамая в 1380 году.",
                R.drawable.event_kulikovo_battle));

        events.add(new HistorycalEvent(
                "Первый полет братьев Райт",
                "Первый в мире полет на самолете с двигателем внутреннего сгорания в 1903 году.",
                R.drawable.event_wright_flight));

        events.add(new HistorycalEvent(
                "Строительство Великой Китайской стены",
                "Крупнейший памятник архитектуры, возведение которого началось в III веке до н.э.",
                R.drawable.event_great_wall));

        events.add(new HistorycalEvent(
                "Изобретение книгопечатания",
                "Иоганн Гутенберг изобрел способ печати подвижными литерами в середине XV века.",
                R.drawable.event_printing_press));

        eventsLiveData.setValue(events);
    }
}