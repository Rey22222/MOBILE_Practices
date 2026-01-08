package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.khasanova.succuforest.domain.repository.AuthRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.LoginUseCase;

public class LoginViewModel extends ViewModel {
    private final LoginUseCase authUseCase;

    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LoginViewModel(AuthRepository authRepository) {
        this.authUseCase = new LoginUseCase(authRepository);
    }

    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Заполните все поля");
            return;
        }

        authUseCase.login(email, password, new AuthRepository.Callback() {
            @Override
            public void onSuccess() {
                loginSuccess.setValue(true);
            }

            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }
}