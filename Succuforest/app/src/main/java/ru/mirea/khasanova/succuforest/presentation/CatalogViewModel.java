package ru.mirea.khasanova.succuforest.presentation;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentCatalogUseCase;

public class CatalogViewModel extends ViewModel {

    private final MutableLiveData<List<Succulent>> succulentsLiveData = new MutableLiveData<>();
    private final GetSucculentCatalogUseCase getSucculentCatalogUseCase;
    private static final String TAG = "CatalogViewModel";

    public CatalogViewModel(SucculentRepositoryImpl repository) {
        this.getSucculentCatalogUseCase = new GetSucculentCatalogUseCase(repository);
        loadCatalog();
    }

    public LiveData<List<Succulent>> getSucculents() {
        return succulentsLiveData;
    }

    public void loadCatalog() {
        Log.d(TAG, "loadCatalog: Запрос данных через UseCase");
        getSucculentCatalogUseCase.execute(new Callback<List<Succulent>>() {
            @Override
            public void onResponse(Call<List<Succulent>> call, Response<List<Succulent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Данные успешно получены, размер: " + response.body().size());
                    succulentsLiveData.setValue(response.body());
                } else {
                    Log.e(TAG, "onResponse: Ошибка сервера " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Succulent>> call, Throwable t) {
                Log.e(TAG, "onFailure: Критическая ошибка сети: " + t.getMessage());
            }
        });
    }
}