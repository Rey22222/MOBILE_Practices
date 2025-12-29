package ru.mirea.khasanova.succuforest.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentCatalogUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.LoginUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvInfo = findViewById(R.id.tvInfo);
        EditText etId = findViewById(R.id.etSucculentId);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnList = findViewById(R.id.btnList);
        Button btnDetails = findViewById(R.id.btnDetails);
        Button btnPredict = findViewById(R.id.btnPredict);

        SucculentRepository repository = new SucculentRepositoryImpl();
        LoginUseCase loginUseCase = new LoginUseCase(repository);
        GetSucculentCatalogUseCase listUseCase = new GetSucculentCatalogUseCase(repository);
        GetSucculentDetailsUseCase detailsUseCase = new GetSucculentDetailsUseCase(repository);
        DetermineSucculentTypeUseCase predictUseCase = new DetermineSucculentTypeUseCase(repository);

        btnLogin.setOnClickListener(v -> {
            String token = loginUseCase.execute();
            tvInfo.setText("Статус: Вход выполнен\nToken: " + token);
        });

        btnList.setOnClickListener(v -> {
            List<Succulent> list = listUseCase.execute();
            StringBuilder sb = new StringBuilder("Каталог Succuforest:\n");
            for (Succulent s : list) {
                sb.append(s.getId()).append(". ").append(s.getName()).append("\n");
            }
            tvInfo.setText(sb.toString());
        });

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
            } catch (NumberFormatException e) {
                tvInfo.setText("Ошибка: Введите корректное число.");
            }
        });

        // 4. AI
        btnPredict.setOnClickListener(v -> {
            String result = predictUseCase.execute();
            tvInfo.setText(result);
        });
    }
}