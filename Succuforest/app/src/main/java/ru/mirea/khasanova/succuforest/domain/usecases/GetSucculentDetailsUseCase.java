package ru.mirea.khasanova.succuforest.domain.usecases;

import retrofit2.Callback;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class GetSucculentDetailsUseCase {
    private final SucculentRepository repository;

    public GetSucculentDetailsUseCase(SucculentRepository repository) {
        this.repository = repository;
    }
    public void execute(int id, Callback<Succulent> callback) {
        repository.getById(id, callback);
    }
}