package ru.mirea.khasanova.succuforest.domain.repository;

public interface AuthRepository {
    interface Callback {
        void onSuccess();
        void onError(String msg);
    }
    void login(String email, String password, Callback callback);
    void register(String email, String password, Callback callback);
}