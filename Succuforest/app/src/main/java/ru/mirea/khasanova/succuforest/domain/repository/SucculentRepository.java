package ru.mirea.khasanova.succuforest.domain.repository;

import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public interface SucculentRepository {
    List<Succulent> getCatalog();
    List<Succulent> getFavorites();
    Succulent getById(int id);
    void toggleFavorite(int id);

    String predictSucculentType();
}