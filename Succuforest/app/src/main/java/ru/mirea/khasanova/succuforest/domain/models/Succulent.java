package ru.mirea.khasanova.succuforest.domain.models;

import java.io.Serializable;

public class Succulent implements Serializable {
    private int id;
    private String name;
    private String price;
    private String imageUrl;
    private String description;
    private boolean isFavorite;

    public Succulent(int id, String name, String price, String imageUrl, String description, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public boolean isFavorite() { return isFavorite; }

    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}