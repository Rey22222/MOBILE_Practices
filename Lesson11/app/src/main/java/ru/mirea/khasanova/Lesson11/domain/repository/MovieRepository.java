package ru.mirea.khasanova.Lesson11.domain.repository;

import ru.mirea.khasanova.Lesson11.domain.model.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}