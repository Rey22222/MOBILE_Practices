package ru.mirea.khasanova.Lesson9.domain.repository;

import ru.mirea.khasanova.Lesson9.domain.model.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}