package ru.mirea.khasanova.succuforest.presentation;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.models.WeatherInfo;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;
import ru.mirea.khasanova.succuforest.domain.repository.WeatherRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetWeatherUseCase;

public class HomeViewModel extends ViewModel {
    private final SucculentRepository succulentRepository;
    private final GetWeatherUseCase getWeatherUseCase;
    private final MutableLiveData<String> searchResult = new MutableLiveData<>();
    private final MutableLiveData<String> aiResult = new MutableLiveData<>();
    private final MediatorLiveData<String> screenStatus = new MediatorLiveData<>();

    private final MutableLiveData<String> weatherText = new MutableLiveData<>("Загрузка погоды...");
    private final MutableLiveData<String> wateringAdvice = new MutableLiveData<>("Анализируем условия...");

    public HomeViewModel(SucculentRepository succulentRepository, WeatherRepository weatherRepository) {
        this.succulentRepository = succulentRepository;
        this.getWeatherUseCase = new GetWeatherUseCase(weatherRepository);

        screenStatus.setValue("Добро пожаловать в Суккулесье!");

        screenStatus.addSource(searchResult, s -> screenStatus.setValue(s));
        screenStatus.addSource(aiResult, s -> screenStatus.setValue(s));

        fetchCurrentWeather();
    }

    public LiveData<String> getScreenStatus() { return screenStatus; }
    public LiveData<String> getWeatherText() { return weatherText; }
    public LiveData<String> getWateringAdvice() { return wateringAdvice; }

    public void searchById(int id) {
        new GetSucculentDetailsUseCase(succulentRepository).execute(id, new Callback<Succulent>() {
            @Override
            public void onResponse(Call<Succulent> call, Response<Succulent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Succulent s = response.body();
                    searchResult.setValue("Найдено: " + s.getName() + " (" + s.getPrice() + ")");
                } else {
                    searchResult.setValue("Растение с ID " + id + " не найдено.");
                }
            }

            @Override
            public void onFailure(Call<Succulent> call, Throwable t) {
                searchResult.setValue("Ошибка сети при поиске");
            }
        });
    }

    public void predictType() {
        aiResult.setValue("Результат AI: " + new DetermineSucculentTypeUseCase(succulentRepository).execute());
    }

    private void fetchCurrentWeather() {
        getWeatherUseCase.execute("Moscow", new GetWeatherUseCase.WeatherCallback() {
            @Override
            public void onResult(WeatherInfo info) {
                weatherText.setValue("Москва: " + info.getTemperature() + "°C, Влажность: " + info.getHumidity() + "%");
                generateAdvice(info);
            }

            @Override
            public void onError(String error) {
                weatherText.setValue("Погода недоступна");
                wateringAdvice.setValue("Не удалось получить рекомендации");
            }
        });
    }

    private void generateAdvice(WeatherInfo info) {
        if (info.getTemperature() > 25) {
            wateringAdvice.setValue("Сейчас жарко. Можно полить, но немного.");
        } else {
            wateringAdvice.setValue("Условия в норме. Полив по графику.");
        }
    }
}