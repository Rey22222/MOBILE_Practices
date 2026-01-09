package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentCatalogUseCase;

public class CatalogViewModel extends ViewModel {

    private final MutableLiveData<List<Succulent>> succulentsLiveData = new MutableLiveData<>();
    private final GetSucculentCatalogUseCase getSucculentCatalogUseCase;

    public CatalogViewModel(SucculentRepositoryImpl repository) {
        this.getSucculentCatalogUseCase = new GetSucculentCatalogUseCase(repository);
        loadCatalog();
    }

    public LiveData<List<Succulent>> getSucculents() {
        return succulentsLiveData;
    }

    public void loadCatalog() {
        List<Succulent> list = getSucculentCatalogUseCase.execute();
        succulentsLiveData.setValue(list);
    }
}