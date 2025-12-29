package ru.mirea.khasanova.succuforest.domain.usecases;

import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class LoginUseCase {
    private SucculentRepository repository;

    public LoginUseCase(SucculentRepository repository) {
        this.repository = repository;
    }

    public String execute() {
        return repository.login("guest", "1234");
    }
}