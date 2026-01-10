package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class DetailsViewModel extends ViewModel {

    private final SucculentRepository repository;
    private final GetSucculentDetailsUseCase getDetailsUseCase;

    private final MutableLiveData<Succulent> succulentLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public DetailsViewModel(SucculentRepository repository) {
        this.repository = repository;
        this.getDetailsUseCase = new GetSucculentDetailsUseCase(repository);
    }

    public LiveData<Succulent> getSucculent() { return succulentLiveData; }
    public LiveData<String> getError() { return errorLiveData; }

    public void loadDetails(int id) {
        getDetailsUseCase.execute(id, new Callback<Succulent>() {
            @Override
            public void onResponse(Call<Succulent> call, Response<Succulent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    succulentLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Ошибка сервера");
                }
            }

            @Override
            public void onFailure(Call<Succulent> call, Throwable t) {
                errorLiveData.setValue("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public void toggleFavorite() {
        Succulent current = succulentLiveData.getValue();
        if (current != null) {
            repository.toggleFavorite(current);
            current.setFavorite(!current.isFavorite());
            succulentLiveData.setValue(current);
        }
    }
}