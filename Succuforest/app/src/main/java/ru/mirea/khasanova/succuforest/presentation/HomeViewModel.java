package ru.mirea.khasanova.succuforest.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class HomeViewModel extends ViewModel {
    private final SucculentRepositoryImpl repository;

    private final MutableLiveData<String> searchResult = new MutableLiveData<>();
    private final MutableLiveData<String> aiResult = new MutableLiveData<>();
    private final MediatorLiveData<String> screenStatus = new MediatorLiveData<>();

    public HomeViewModel(SucculentRepositoryImpl repository) {
        this.repository = repository;
        screenStatus.setValue("Добро пожаловать в Суккулесье!");
        screenStatus.addSource(searchResult, s -> screenStatus.setValue(s));
        screenStatus.addSource(aiResult, s -> screenStatus.setValue(s));
    }

    public LiveData<String> getScreenStatus() {
        return screenStatus;
    }

    public void searchById(int id) {
        Succulent succulent = new GetSucculentDetailsUseCase(repository).execute(id);
        searchResult.setValue(succulent != null ? succulent.getName() + " - " + succulent.getPrice() : "Не найдено");
    }

    public void predictType() {
        String result = new DetermineSucculentTypeUseCase(repository).execute();
        aiResult.setValue(result);
    }
}