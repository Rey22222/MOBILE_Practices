package ru.mirea.khasanova.succuforest.data.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.khasanova.succuforest.data.network.SucculentApiService;
import ru.mirea.khasanova.succuforest.data.storage.room.SucculentDao;
import ru.mirea.khasanova.succuforest.data.storage.room.SucculentEntity;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class SucculentRepositoryImpl implements SucculentRepository {

    private final SucculentApiService apiService;
    private final SucculentDao dao;
    private static final String TAG = "REPO_DEBUG";

    public SucculentRepositoryImpl(SucculentApiService apiService, SucculentDao dao) {
        this.apiService = apiService;
        this.dao = dao;
    }

    @Override
    public void getCatalog(Callback<List<Succulent>> callback) {
        apiService.getSucculents().enqueue(new Callback<List<Succulent>>() {
            @Override
            public void onResponse(Call<List<Succulent>> call, Response<List<Succulent>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Succulent> netList = response.body();

                    for (Succulent s : netList) {
                        SucculentEntity local = dao.getById(s.getId());
                        if (local != null) {
                            s.setFavorite(local.isFavorite);
                        }
                    }
                    callback.onResponse(call, Response.success(netList));
                }
            }

            @Override
            public void onFailure(Call<List<Succulent>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    @Override
    public List<Succulent> getFavorites() {
        Log.d(TAG, "Запрос в локальную БД (Избранное)");
        List<SucculentEntity> entities = dao.getFavorites();
        List<Succulent> domainList = new ArrayList<>();
        for (SucculentEntity e : entities) {
            domainList.add(new Succulent(e.id, e.name, e.price, e.imageUrl, e.description, e.isFavorite));
        }
        return domainList;
    }

    @Override
    public void toggleFavorite(Succulent s) {
        new Thread(() -> {
            SucculentEntity local = dao.getById(s.getId());

            if (local == null) {
                SucculentEntity newEntity = new SucculentEntity(
                        s.getId(), s.getName(), s.getPrice(), s.getImageUrl(), s.getDescription(), true
                );
                dao.insertAll(java.util.Collections.singletonList(newEntity));
                Log.d("REPO_DEBUG", "Впервые сохранено в БД: " + s.getName());
            } else {
                SucculentEntity updatedEntity = new SucculentEntity(
                        s.getId(), s.getName(), s.getPrice(), s.getImageUrl(), s.getDescription(), !local.isFavorite
                );
                dao.insertAll(java.util.Collections.singletonList(updatedEntity));
                Log.d("REPO_DEBUG", "Данные в БД обновлены на свежие для: " + s.getName());
            }
        }).start();
    }

    @Override
    public void getById(int id, Callback<Succulent> callback) {
        apiService.getSucculentById(id).enqueue(new Callback<Succulent>() {
            @Override
            public void onResponse(Call<Succulent> call, Response<Succulent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Succulent s = response.body();
                    SucculentEntity local = dao.getById(id);
                    if (local != null) {
                        s.setFavorite(local.isFavorite);
                    }
                    callback.onResponse(call, Response.success(s));
                }
            }
            @Override
            public void onFailure(Call<Succulent> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    @Override
    public String predictSucculentType() {
        return "Анализ изображения завершен...\nРезультат: Эхеверия (99%)";
    }
}