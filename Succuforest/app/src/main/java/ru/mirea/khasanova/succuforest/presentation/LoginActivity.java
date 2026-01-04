package ru.mirea.khasanova.succuforest.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.khasanova.succuforest.data.repository.AuthRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.prefs.ClientPrefs;
import ru.mirea.khasanova.succuforest.domain.repository.AuthRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.LoginUseCase;
import ru.mirea.khasanova.succuforest.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginUseCase useCase = new LoginUseCase(new AuthRepositoryImpl(new ClientPrefs(this)));
        EditText email = findViewById(R.id.etEmail);
        EditText pass = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v ->
                useCase.login(email.getText().toString(), pass.getText().toString(), new AuthRepository.Callback() {
                    @Override public void onSuccess() { navToMain(); }
                    @Override public void onError(String msg) { showToast(msg); }
                }));

        findViewById(R.id.btnRegister).setOnClickListener(v ->
                useCase.register(email.getText().toString(), pass.getText().toString(), new AuthRepository.Callback() {
                    @Override public void onSuccess() { navToMain(); }
                    @Override public void onError(String msg) { showToast(msg); }
                }));
    }

    private void navToMain() {
        startActivity(new Intent(this, RootActivity.class));
        finish();
    }
    private void showToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
}