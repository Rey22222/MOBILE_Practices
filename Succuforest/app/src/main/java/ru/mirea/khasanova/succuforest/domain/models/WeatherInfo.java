package ru.mirea.khasanova.succuforest.domain.models;

public class WeatherInfo {
    private final float temperature;
    private final int humidity;

    public WeatherInfo(float temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public float getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
}