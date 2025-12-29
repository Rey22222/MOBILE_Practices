package ru.mirea.khasanova.succuforest.domain.repository;

import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public interface SucculentRepository {
    String login(String email, String password);
    List<Succulent> getSucculents();
    Succulent getSucculentById(int id);
    String predictSucculentType();
}