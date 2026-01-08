package ru.mirea.khasanova.succuforest.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.khasanova.succuforest.data.repository.AuthRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.prefs.ClientPrefs;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;

public class SuccuViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public SuccuViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            AuthRepositoryImpl authRepository = new AuthRepositoryImpl(
                    new ClientPrefs(context)
            );
            return (T) new LoginViewModel(authRepository);
        }

        SucculentRepositoryImpl succulentRepository = new SucculentRepositoryImpl(
                new ru.mirea.khasanova.data.network.MockNetworkApi(),
                AppDatabase.getInstance(context).dao()
        );

        if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            return (T) new CatalogViewModel(succulentRepository);
        }

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(succulentRepository);
        }

        if (modelClass.isAssignableFrom(FavoritesViewModel.class)) {
            return (T) new FavoritesViewModel(succulentRepository);
        }

        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(succulentRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}