package ru.mirea.khasanova.succuforest.domain.usecases;

import ru.mirea.khasanova.succuforest.domain.repository.AuthRepository;

public class LoginUseCase {
    private final AuthRepository repository;

    public LoginUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void login(String email, String pass, AuthRepository.Callback cb) {
        repository.login(email, pass, cb);
    }

    public void register(String email, String pass, AuthRepository.Callback cb) {
        repository.register(email, pass, cb);
    }
}