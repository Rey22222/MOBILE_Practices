package ru.mirea.khasanova.succuforest.domain.repository;

import java.util.List;
import retrofit2.Callback;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public interface SucculentRepository {
    void getCatalog(Callback<List<Succulent>> callback);
    void getById(int id, Callback<Succulent> callback);
    List<Succulent> getFavorites();
    void toggleFavorite(Succulent succulent);
    String predictSucculentType();
}