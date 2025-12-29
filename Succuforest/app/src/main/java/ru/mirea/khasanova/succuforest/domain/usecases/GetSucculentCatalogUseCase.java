package ru.mirea.khasanova.succuforest.domain.usecases;

import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class GetSucculentCatalogUseCase {
    private SucculentRepository repository;

    public GetSucculentCatalogUseCase(SucculentRepository repository) {
        this.repository = repository;
    }

    public List<Succulent> execute() {
        return repository.getSucculents();
    }
}