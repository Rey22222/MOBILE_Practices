package ru.mirea.khasanova.succuforest.data.storage.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SucculentDao {
    @Query("SELECT * FROM succulents")
    List<SucculentEntity> getAll();

    @Query("SELECT * FROM succulents WHERE id = :id")
    SucculentEntity getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SucculentEntity> list);

    @Query("UPDATE succulents SET isFavorite = :isFav WHERE id = :id")
    void updateFavorite(int id, boolean isFav);

    @Query("SELECT * FROM succulents WHERE isFavorite = 1")
    List<SucculentEntity> getFavorites();
}