package ru.mirea.khasanova.Lesson11.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.khasanova.Lesson11.domain.model.Movie;
import ru.mirea.khasanova.Lesson11.domain.repository.MovieRepository;
import ru.mirea.khasanova.Lesson11.domain.usecase.GetFavoriteMovieUseCase;
import ru.mirea.khasanova.Lesson11.domain.usecase.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private MutableLiveData<String> resultLive = new MutableLiveData<>();
    public LiveData<String> getResultLive() {
        return resultLive;
    }

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(String movieName) {
        Movie movie = new Movie(2, movieName);
        boolean success = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        if (success) {
            resultLive.setValue("Фильм сохранён!");
        } else {
            resultLive.setValue("Ошибка: Название пустое");
        }
    }

    public void load() {
        Movie movie = new GetFavoriteMovieUseCase(movieRepository).execute();
        resultLive.setValue("Избранный фильм: " + movie.getName());
    }
}