package ru.mirea.khasanova.fragmentapp.domain.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String status;

    public Task(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getStatus() { return status; }
}