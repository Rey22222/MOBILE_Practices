package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
        SucculentRepositoryImpl repository = new SucculentRepositoryImpl(
                new ru.mirea.khasanova.data.network.MockNetworkApi(),
                AppDatabase.getInstance(this).dao()
        );

        GetSucculentDetailsUseCase detailsUseCase = new GetSucculentDetailsUseCase(repository);

        int id = getIntent().getIntExtra("ID", -1);

        Succulent s = detailsUseCase.execute(id);

        if (s != null) {
            ((TextView)findViewById(R.id.tvDetailName)).setText(s.getName());
            ((TextView)findViewById(R.id.tvDetailPrice)).setText(s.getPrice());
            ((TextView)findViewById(R.id.tvDetailDescription)).setText(s.getDescription());

            int resId = getResources().getIdentifier(s.getImageUrl(), "drawable", getPackageName());
            if (resId == 0) resId = android.R.drawable.ic_menu_gallery;

            Glide.with(this).load(resId).into((ImageView)findViewById(R.id.ivDetailImage));

            Button btn = findViewById(R.id.btnAddFavorite);
            btn.setText(s.isFavorite() ? "В избранном" : "В избранное");

            btn.setOnClickListener(v -> {
                repository.toggleFavorite(id);
                Toast.makeText(this, "Статус обновлен!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}