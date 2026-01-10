package ru.mirea.khasanova.fragmentmanagerapp;

import java.io.Serializable;

public class Country implements Serializable {
    private String name;
    private String description;

    public Country(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}