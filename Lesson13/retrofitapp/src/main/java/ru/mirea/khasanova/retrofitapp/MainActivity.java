package ru.mirea.khasanova.retrofitapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://dummyjson.com/";
    private static final String TAG = "RETROFIT_DEBUG";

    private ApiService apiService;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Picasso.get().setLoggingEnabled(true);
        loadTodos();
    }

    private void loadTodos() {
        apiService.getTodos().enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, Response<TodoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> todoList = response.body().getTodos();
                    TodoAdapter adapter = new TodoAdapter(todoList, MainActivity.this, todo -> {
                        updateTodoOnServer(todo);
                    });
                    recyclerView.setAdapter(adapter);

                    Log.d(TAG, "Данные успешно загружены. Количество: " + todoList.size());
                } else {
                    Log.e(TAG, "Ошибка сервера: " + response.code());
                    Toast.makeText(MainActivity.this, "Сервер вернул ошибку: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TodoResponse> call, Throwable t) {
                Log.e(TAG, "Критическая ошибка сети: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Ошибка сети: проверьте интернет", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateTodoOnServer(Todo todo) {
        apiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Задача " + todo.getId() + " успешно обновлена");
                    Toast.makeText(MainActivity.this, "Обновлено на сервере", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e(TAG, "Не удалось отправить PUT запрос: " + t.getMessage());
            }
        });
    }
}