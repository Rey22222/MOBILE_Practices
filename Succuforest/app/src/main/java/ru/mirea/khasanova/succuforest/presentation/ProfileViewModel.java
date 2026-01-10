package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();

    public ProfileViewModel() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userEmail.setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
    }

    public LiveData<String> getUserEmail() { return userEmail; }
    public LiveData<Boolean> getLogoutEvent() { return logoutEvent; }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        logoutEvent.setValue(true);
    }
}