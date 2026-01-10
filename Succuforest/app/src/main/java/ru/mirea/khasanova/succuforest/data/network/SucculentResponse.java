package ru.mirea.khasanova.succuforest.data.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class SucculentResponse {
    @SerializedName("products")
    private List<Succulent> products;

    public List<Succulent> getProducts() {
        return products;
    }
}