package ru.mirea.khasanova.succuforest.domain.usecases;

import ru.mirea.khasanova.succuforest.domain.models.WeatherInfo;
import ru.mirea.khasanova.succuforest.domain.repository.WeatherRepository;

public class GetWeatherUseCase {
    private final WeatherRepository repository;

    public GetWeatherUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    public void execute(String city, WeatherCallback callback) {
        repository.getWeather(city, callback);
    }

    public interface WeatherCallback {
        void onResult(WeatherInfo info);
        void onError(String error);
    }
}