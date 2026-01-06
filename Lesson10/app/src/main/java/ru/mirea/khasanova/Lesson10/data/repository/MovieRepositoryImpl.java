package ru.mirea.khasanova.Lesson10.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import ru.mirea.khasanova.Lesson10.domain.model.Movie;
import ru.mirea.khasanova.Lesson10.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String PREFS_NAME = "movie_prefs";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String DEFAULT_MOVIE_NAME = "Beauty and the Beast";

    private final SharedPreferences sharedPreferences;

    public MovieRepositoryImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        if (movie.getName().isEmpty()) {
            return false;
        }
        sharedPreferences.edit()
                .putInt(KEY_MOVIE_ID, movie.getId())
                .putString(KEY_MOVIE_NAME, movie.getName())
                .apply();
        return true;
    }

    @Override
    public Movie getMovie() {
        int id = sharedPreferences.getInt(KEY_MOVIE_ID, 0);
        String name = sharedPreferences.getString(KEY_MOVIE_NAME, DEFAULT_MOVIE_NAME);
        return new Movie(id, name);
    }
}