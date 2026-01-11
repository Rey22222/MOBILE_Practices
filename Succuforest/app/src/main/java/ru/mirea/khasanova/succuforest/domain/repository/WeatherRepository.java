package ru.mirea.khasanova.succuforest.domain.repository;

import ru.mirea.khasanova.succuforest.domain.usecases.GetWeatherUseCase;

public interface WeatherRepository {
    void getWeather(String city, GetWeatherUseCase.WeatherCallback callback);
}