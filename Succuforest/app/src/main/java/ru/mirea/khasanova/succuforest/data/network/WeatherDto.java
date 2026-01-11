package ru.mirea.khasanova.succuforest.data.network;

import com.google.gson.annotations.SerializedName;

public class WeatherDto {
    @SerializedName("main")
    public MainData main;

    public static class MainData {
        @SerializedName("temp")
        public float temp;

        @SerializedName("humidity")
        public int humidity;
    }
}