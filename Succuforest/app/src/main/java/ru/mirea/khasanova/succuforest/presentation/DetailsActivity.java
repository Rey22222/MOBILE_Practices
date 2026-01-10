package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import ru.mirea.khasanova.succuforest.R;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewModel = new ViewModelProvider(this, new SuccuViewModelFactory(this))
                .get(DetailsViewModel.class);

        int id = getIntent().getIntExtra("ID", -1);

        viewModel.getSucculent().observe(this, succulent -> {
            ((TextView)findViewById(R.id.tvDetailName)).setText(succulent.getName());
            ((TextView)findViewById(R.id.tvDetailDescription)).setText(succulent.getDescription());
            ((TextView)findViewById(R.id.tvDetailPrice)).setText(succulent.getPrice());

            Picasso.get()
                    .load(succulent.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.stat_notify_error)
                    .fit()
                    .centerCrop()
                    .into((ImageView)findViewById(R.id.ivDetailImage));
        });

        viewModel.getError().observe(this, errorMsg -> {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        });

        if (id != -1) {
            viewModel.loadDetails(id);
        }
        Button btn = findViewById(R.id.btnAddFavorite);
        viewModel.getSucculent().observe(this, succulent -> {
            if (succulent != null) {
                if (succulent.isFavorite()) {
                    btn.setText("В избранном");
                    btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    btn.setText("В избранное");
                    btn.setBackgroundColor(getResources().getColor(R.color.sage_primary));
                }

                btn.setOnClickListener(v -> {
                    viewModel.toggleFavorite();
                    Toast.makeText(this, "Список избранного обновлен", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}