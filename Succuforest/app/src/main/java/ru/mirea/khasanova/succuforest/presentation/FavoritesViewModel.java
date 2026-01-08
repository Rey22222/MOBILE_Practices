package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class FavoritesViewModel extends ViewModel {
    private final SucculentRepositoryImpl repository;
    private final MutableLiveData<List<Succulent>> favorites = new MutableLiveData<>();

    public FavoritesViewModel(SucculentRepositoryImpl repository) {
        this.repository = repository;
        refresh();
    }

    public LiveData<List<Succulent>> getFavorites() { return favorites; }

    public void refresh() {
        favorites.setValue(repository.getFavorites());
    }
}