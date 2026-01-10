package ru.mirea.khasanova.retrofitapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Todo {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("todo")
    @Expose
    private String todo;

    @SerializedName("completed")
    @Expose
    private Boolean completed;

    public Todo() {}

    public Todo(Integer id, String todo, Boolean completed) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
    }

    public Integer getId() { return id; }
    public String getTodo() { return todo; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}