package ru.mirea.khasanova.fragmentapp.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.khasanova.fragmentapp.domain.models.Task;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedTask = new MutableLiveData<>();

    public void selectTask(Task task) {
        selectedTask.setValue(task);
    }

    public LiveData<Task> getSelectedTask() {
        return selectedTask;
    }
}