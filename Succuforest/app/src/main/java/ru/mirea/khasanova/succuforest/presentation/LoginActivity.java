package ru.mirea.khasanova.succuforest.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.AuthRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.prefs.ClientPrefs;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginViewModel vm = new ViewModelProvider(this, new SuccuViewModelFactory(this))
                .get(LoginViewModel.class);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        vm.getLoginSuccess().observe(this, success -> {
            if (success) {
                Intent intent = new Intent(this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        });

        vm.getErrorMessage().observe(this, msg -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            vm.login(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
        });

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
        });
    }
}