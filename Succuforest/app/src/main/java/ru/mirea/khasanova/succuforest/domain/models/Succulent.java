package ru.mirea.khasanova.succuforest.domain.models;

public class Succulent {
    private int id;
    private String name;
    private String price;

    public Succulent(int id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
}