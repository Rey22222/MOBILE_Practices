package ru.mirea.khasanova.Lesson10.domain.repository;

import ru.mirea.khasanova.Lesson10.domain.model.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}