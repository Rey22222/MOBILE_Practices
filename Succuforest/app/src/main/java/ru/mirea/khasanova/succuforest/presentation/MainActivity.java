package ru.mirea.khasanova.succuforest.presentation;

import ru.mirea.khasanova.succuforest.data.network.SucculentResponse;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.network.SucculentApiService;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://69621edbd9d64c761906fc1b.mockapi.io/succulents";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SucculentApiService api = retrofit.create(SucculentApiService.class);

        api.getSucculents().enqueue(new Callback<List<Succulent>>() {
            @Override
            public void onResponse(Call<List<Succulent>> call, Response<List<Succulent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Succulent> list = response.body();
                    SucculentAdapter adapter = new SucculentAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Succulent>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            }
        });
    }
}