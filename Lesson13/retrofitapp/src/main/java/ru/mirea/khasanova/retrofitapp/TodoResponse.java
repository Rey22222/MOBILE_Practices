package ru.mirea.khasanova.retrofitapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TodoResponse {
    @SerializedName("todos")
    @Expose
    private List<Todo> todos;

    public List<Todo> getTodos() { return todos; }
}