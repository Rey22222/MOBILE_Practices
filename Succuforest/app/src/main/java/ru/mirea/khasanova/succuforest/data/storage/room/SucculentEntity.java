package ru.mirea.khasanova.succuforest.data.storage.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "succulents")
public class SucculentEntity {
    @PrimaryKey
    public int id;
    public String name;
    public String price;
    public String imageUrl;
    public String description;
    public boolean isFavorite;

    public SucculentEntity() {}

    public SucculentEntity(int id, String name, String price, String imageUrl, String description, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isFavorite = isFavorite;
    }

}