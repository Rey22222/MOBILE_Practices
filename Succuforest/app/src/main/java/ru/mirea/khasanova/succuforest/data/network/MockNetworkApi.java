package ru.mirea.khasanova.data.network;

import java.util.ArrayList;
import java.util.List;

public class MockNetworkApi {
    public static class Dto {
        public int id;
        public String name;
        public String price;
        public String img;
        public String desc;

        public Dto(int id, String name, String price, String img, String desc) {
            this.id = id; this.name = name; this.price = price; this.img = img; this.desc = desc;
        }
    }

    public List<Dto> fetchSucculents() {
        List<Dto> list = new ArrayList<>();
        list.add(new Dto(1, "Echeveria Lola", "350 ₽", "succ_1", "Красивая розеточная эхеверия."));
        list.add(new Dto(2, "Aloe Vera", "200 ₽", "succ_2", "Лечебное и неприхотливое."));
        list.add(new Dto(3, "Haworthia Fasciata", "450 ₽", "succ_3", "Полосатый суккулент для полутени."));
        return list;
    }
}