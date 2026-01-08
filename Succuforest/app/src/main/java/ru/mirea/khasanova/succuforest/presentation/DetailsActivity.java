package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DetailsViewModel vm = new ViewModelProvider(this, new SuccuViewModelFactory(this))
                .get(DetailsViewModel.class);

        int id = getIntent().getIntExtra("ID", -1);
        vm.loadDetails(id);

        vm.getSucculent().observe(this, s -> {
            if (s == null) return;
            ((TextView)findViewById(R.id.tvDetailName)).setText(s.getName());
            ((TextView)findViewById(R.id.tvDetailPrice)).setText(s.getPrice());
            ((TextView)findViewById(R.id.tvDetailDescription)).setText(s.getDescription());

            int resId = getResources().getIdentifier(s.getImageUrl(), "drawable", getPackageName());
            Glide.with(this).load(resId != 0 ? resId : android.R.drawable.ic_menu_gallery).into((ImageView)findViewById(R.id.ivDetailImage));

            Button btn = findViewById(R.id.btnAddFavorite);
            btn.setText(s.isFavorite() ? "В избранном" : "В избранное");
            btn.setOnClickListener(v -> vm.toggleFavorite(id));
        });
    }
}