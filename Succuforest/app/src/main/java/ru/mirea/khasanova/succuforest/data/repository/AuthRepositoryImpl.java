package ru.mirea.khasanova.succuforest.data.repository;


import com.google.firebase.auth.FirebaseAuth;

import ru.mirea.khasanova.succuforest.data.storage.prefs.ClientPrefs;
import ru.mirea.khasanova.succuforest.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final ClientPrefs prefs;

    public AuthRepositoryImpl(ClientPrefs prefs) {
        this.prefs = prefs;
    }

    @Override
    public void login(String email, String password, Callback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(r -> {
                    prefs.saveUser(email);
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    @Override
    public void register(String email, String password, Callback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(r -> {
                    prefs.saveUser(email);
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}