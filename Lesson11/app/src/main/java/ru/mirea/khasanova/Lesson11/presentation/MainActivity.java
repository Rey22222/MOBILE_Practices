package ru.mirea.khasanova.Lesson11.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.khasanova.Lesson11.R;


public class MainActivity extends AppCompatActivity {

    private EditText editTextMovie;
    private TextView textViewResult;
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        textViewResult = findViewById(R.id.textViewResult);

        vm = new ViewModelProvider(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);
        vm.getResultLive().observe(this, text -> {
            textViewResult.setText(text);
        });
        Button buttonSave = findViewById(R.id.buttonSaveMovie);
        buttonSave.setOnClickListener(v -> {
            String movieName = editTextMovie.getText().toString();
            vm.save(movieName);
        });
        Button buttonGet = findViewById(R.id.buttonGetMovie);
        buttonGet.setOnClickListener(v -> {
            vm.load();
        });
    }
}