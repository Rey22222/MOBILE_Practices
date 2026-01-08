package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentCatalogUseCase;

public class CatalogViewModel extends ViewModel {
    private final MutableLiveData<List<Succulent>> succulents = new MutableLiveData<>();
    private final GetSucculentCatalogUseCase getCatalogUseCase;

    public CatalogViewModel(SucculentRepositoryImpl repository) {
        this.getCatalogUseCase = new GetSucculentCatalogUseCase(repository);
        loadCatalog();
    }

    public LiveData<List<Succulent>> getSucculents() { return succulents; }

    public void loadCatalog() {
        succulents.setValue(getCatalogUseCase.execute());
    }
}