package ru.mirea.khasanova.navigationdrawerapp;

public class Plant {
    private int imageResId;
    private String name;

    public Plant(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
}