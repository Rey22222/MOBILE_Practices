package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvInfo = findViewById(R.id.tvInfo);
        EditText etId = findViewById(R.id.etSucculentId);
        Button btnDetails = findViewById(R.id.btnDetails);
        Button btnPredict = findViewById(R.id.btnPredict);

        ru.mirea.khasanova.data.network.MockNetworkApi api = new ru.mirea.khasanova.data.network.MockNetworkApi();
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        SucculentRepository repository = new SucculentRepositoryImpl(api, db.dao());

        GetSucculentDetailsUseCase detailsUseCase = new GetSucculentDetailsUseCase(repository);
        DetermineSucculentTypeUseCase predictUseCase = new DetermineSucculentTypeUseCase(repository);

        btnDetails.setOnClickListener(v -> {
            String idString = etId.getText().toString();
            if (idString.isEmpty()) {
                Toast.makeText(this, "Введите число!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int id = Integer.parseInt(idString);
                Succulent item = detailsUseCase.execute(id);
                if (item != null) {
                    tvInfo.setText("Найден товар:\n" + item.getName() + "\nЦена: " + item.getPrice());
                } else {
                    tvInfo.setText("Ошибка: Товар с ID " + id + " не найден.");
                }
            } catch (Exception e) {
                tvInfo.setText("Ошибка ввода");
            }
        });

        btnPredict.setOnClickListener(v -> {
            String result = predictUseCase.execute();
            tvInfo.setText(result);
        });
    }
}