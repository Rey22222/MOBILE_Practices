package ru.mirea.khasanova.succuforest.domain.usecases;

import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class GetSucculentDetailsUseCase {
    private SucculentRepository repository;

    public GetSucculentDetailsUseCase(SucculentRepository repository) {
        this.repository = repository;
    }

    public Succulent execute(int id) {
        return repository.getSucculentById(id);
    }
}