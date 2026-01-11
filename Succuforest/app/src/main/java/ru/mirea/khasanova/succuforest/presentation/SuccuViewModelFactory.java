package ru.mirea.khasanova.succuforest.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.khasanova.succuforest.data.network.SucculentApiService;
import ru.mirea.khasanova.succuforest.data.repository.AuthRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.repository.WeatherRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.prefs.ClientPrefs;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.data.storage.room.SucculentDao;
import ru.mirea.khasanova.succuforest.domain.repository.WeatherRepository;

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
            AuthRepositoryImpl authRepository = new AuthRepositoryImpl(new ClientPrefs(context));
            return (T) new LoginViewModel(authRepository);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://69621edbd9d64c761906fc1b.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SucculentApiService apiService = retrofit.create(SucculentApiService.class);
        SucculentDao dao = AppDatabase.getInstance(context).dao();

        SucculentRepositoryImpl succulentRepository = new SucculentRepositoryImpl(apiService, dao);

        WeatherRepository weatherRepository = new WeatherRepositoryImpl();

        if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            return (T) new CatalogViewModel(succulentRepository);
        }

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(succulentRepository, weatherRepository);
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