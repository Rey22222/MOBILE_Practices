package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class DetailsViewModel extends ViewModel {
    private final SucculentRepositoryImpl repository;
    private final MutableLiveData<Succulent> succulent = new MutableLiveData<>();

    public DetailsViewModel(SucculentRepositoryImpl repository) {
        this.repository = repository;
    }

    public LiveData<Succulent> getSucculent() { return succulent; }

    public void loadDetails(int id) {
        succulent.setValue(new GetSucculentDetailsUseCase(repository).execute(id));
    }

    public void toggleFavorite(int id) {
        repository.toggleFavorite(id);
        loadDetails(id);
    }
}