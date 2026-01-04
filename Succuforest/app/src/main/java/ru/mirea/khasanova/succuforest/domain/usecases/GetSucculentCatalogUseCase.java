package ru.mirea.khasanova.succuforest.domain.usecases;

import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class GetSucculentCatalogUseCase {
    private final SucculentRepository repository;

    public GetSucculentCatalogUseCase(SucculentRepository repository) {
        this.repository = repository;
    }

    public List<Succulent> getCatalog() {
        return repository.getCatalog();
    }

    public List<Succulent> getFavorites() {
        return repository.getFavorites();
    }

    public Succulent getById(int id) {
        return repository.getById(id);
    }

    public void toggleFavorite(int id) {
        repository.toggleFavorite(id);
    }

    public List<Succulent> execute() {
        return repository.getCatalog();
    }
}