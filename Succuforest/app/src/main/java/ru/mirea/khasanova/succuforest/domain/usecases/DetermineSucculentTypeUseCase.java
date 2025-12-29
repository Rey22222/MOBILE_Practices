package ru.mirea.khasanova.succuforest.domain.usecases;

import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class DetermineSucculentTypeUseCase {
    private SucculentRepository repository;

    public DetermineSucculentTypeUseCase(SucculentRepository repository) {
        this.repository = repository;
    }

    public String execute() {
        return repository.predictSucculentType();
    }
}