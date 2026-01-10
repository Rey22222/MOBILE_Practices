package ru.mirea.khasanova.succuforest.presentation;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class HomeViewModel extends ViewModel {
    private final SucculentRepositoryImpl repository;

    private final MutableLiveData<String> searchResult = new MutableLiveData<>();
    private final MutableLiveData<String> aiResult = new MutableLiveData<>();

    private final MediatorLiveData<String> screenStatus = new MediatorLiveData<>();

    public HomeViewModel(SucculentRepositoryImpl repository) {
        this.repository = repository;
        screenStatus.setValue("Добро пожаловать в Суккулесье!");
        screenStatus.addSource(searchResult, s -> screenStatus.setValue(s));
        screenStatus.addSource(aiResult, s -> screenStatus.setValue(s));
    }

    public LiveData<String> getScreenStatus() { return screenStatus; }

    public void searchById(int id) {
        new GetSucculentDetailsUseCase(repository).execute(id, new Callback<Succulent>() {
            @Override
            public void onResponse(Call<Succulent> call, Response<Succulent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Succulent s = response.body();
                    searchResult.setValue("Найдено: " + s.getName() + " (" + s.getPrice() + ")");
                } else {
                    searchResult.setValue("Растение с ID " + id + " не найдено на сервере.");
                }
            }

            @Override
            public void onFailure(Call<Succulent> call, Throwable t) {
                searchResult.setValue("Ошибка сети: " + t.getMessage());
                Log.e("HomeViewModel", "Network error", t);
            }
        });
    }

    public void predictType() {
        aiResult.setValue("Результат AI: " + new DetermineSucculentTypeUseCase(repository).execute());
    }
}