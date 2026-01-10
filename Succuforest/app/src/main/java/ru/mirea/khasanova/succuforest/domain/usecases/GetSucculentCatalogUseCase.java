package ru.mirea.khasanova.succuforest.domain.usecases;

import java.util.List;
import retrofit2.Callback;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class GetSucculentCatalogUseCase {
    private final SucculentRepository repository;

    public GetSucculentCatalogUseCase(SucculentRepository repository) {
        this.repository = repository;
    }
    public void execute(Callback<List<Succulent>> callback) {
        repository.getCatalog(callback);
    }
}