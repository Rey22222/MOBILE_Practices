package ru.mirea.khasanova.recyclerviewapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EventViewModel viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        viewModel.getEvents().observe(this, events -> {
            EventAdapter adapter = new EventAdapter(events);
            recyclerView.setAdapter(adapter);
        });

        viewModel.loadEvents();
    }
}