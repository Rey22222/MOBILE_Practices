package ru.mirea.khasanova.Lesson11.domain.usecase;

import ru.mirea.khasanova.Lesson11.domain.model.Movie;
import ru.mirea.khasanova.Lesson11.domain.repository.MovieRepository;

public class GetFavoriteMovieUseCase {
    private final MovieRepository movieRepository;

    public GetFavoriteMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}