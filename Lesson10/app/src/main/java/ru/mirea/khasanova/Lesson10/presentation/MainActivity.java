package ru.mirea.khasanova.Lesson10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.mirea.khasanova.Lesson10.data.repository.MovieRepositoryImpl;
import ru.mirea.khasanova.Lesson10.domain.model.Movie;
import ru.mirea.khasanova.Lesson10.domain.usecase.GetFavoriteMovieUseCase;
import ru.mirea.khasanova.Lesson10.domain.usecase.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMovie;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        textViewResult = findViewById(R.id.textViewResult);
        MovieRepositoryImpl movieRepository = new MovieRepositoryImpl(this);
        SaveMovieToFavoriteUseCase saveUseCase = new SaveMovieToFavoriteUseCase(movieRepository);
        GetFavoriteMovieUseCase getUseCase = new GetFavoriteMovieUseCase(movieRepository);
        Button buttonSave = findViewById(R.id.buttonSaveMovie);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = editTextMovie.getText().toString();
                Movie movie = new Movie(2, movieName);
                boolean success = saveUseCase.execute(movie);
                if (success) {
                    Toast.makeText(MainActivity.this, "Фильм сохранён!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Название не может быть пустым", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonGet = findViewById(R.id.buttonGetMovie);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie favoriteMovie = getUseCase.execute();
                textViewResult.setText("Избранный фильм: " + favoriteMovie.getName());
            }
        });
    }
}