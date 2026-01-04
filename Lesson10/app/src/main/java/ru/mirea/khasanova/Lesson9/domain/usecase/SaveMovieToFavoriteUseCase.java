package ru.mirea.khasanova.Lesson9.domain.usecase;

import ru.mirea.khasanova.Lesson9.domain.model.Movie;
import ru.mirea.khasanova.Lesson9.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private final MovieRepository movieRepository;

    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(Movie movie) {
        if (movie.getName().isEmpty()) {
            return false;
        } else {
            return movieRepository.saveMovie(movie);
        }
    }
}