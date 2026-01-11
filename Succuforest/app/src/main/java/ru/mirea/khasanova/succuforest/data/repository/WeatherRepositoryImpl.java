package ru.mirea.khasanova.succuforest.data.repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.khasanova.succuforest.data.network.WeatherApiService;
import ru.mirea.khasanova.succuforest.data.network.WeatherDto;
import ru.mirea.khasanova.succuforest.domain.models.WeatherInfo;
import ru.mirea.khasanova.succuforest.domain.repository.WeatherRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.GetWeatherUseCase;

public class WeatherRepositoryImpl implements WeatherRepository {
    private final WeatherApiService apiService;
    private final String API_KEY = "63a9c03c057166818fe2c16843aa236b";

    public WeatherRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiService = retrofit.create(WeatherApiService.class);
    }

    @Override
    public void getWeather(String city, GetWeatherUseCase.WeatherCallback callback) {
        apiService.getWeather(city, API_KEY, "metric").enqueue(new Callback<WeatherDto>() {
            @Override
            public void onResponse(Call<WeatherDto> call, Response<WeatherDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherDto dto = response.body();
                    callback.onResult(new WeatherInfo(dto.main.temp, dto.main.humidity));
                } else {
                    callback.onError("Ошибка сервера погоды");
                }
            }

            @Override
            public void onFailure(Call<WeatherDto> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}