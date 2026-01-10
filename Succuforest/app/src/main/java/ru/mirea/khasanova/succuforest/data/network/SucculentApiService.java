package ru.mirea.khasanova.succuforest.data.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public interface SucculentApiService {
    @GET("succulents")
    Call<List<Succulent>> getSucculents();

    @GET("succulents/{id}")
    Call<Succulent> getSucculentById(@Path("id") int id);
}