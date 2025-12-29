package ru.mirea.khasanova.succuforest.data.repository;

import java.util.ArrayList;
import java.util.List;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class SucculentRepositoryImpl implements SucculentRepository {

    private List<Succulent> succulents;

    public SucculentRepositoryImpl() {
        succulents = new ArrayList<>();
        succulents.add(new Succulent(1, "Эхеверия 'Lola'", "350 ₽"));
        succulents.add(new Succulent(2, "Алоэ Вера", "200 ₽"));
        succulents.add(new Succulent(3, "Хавортия Купера", "450 ₽"));
    }

    @Override
    public String login(String email, String password) {
        return "Access_Token_G25_SuccuUser";
    }

    @Override
    public List<Succulent> getSucculents() {
        return succulents;
    }

    @Override
    public Succulent getSucculentById(int id) {
        for (Succulent s : succulents) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String predictSucculentType() {
        return "Анализ изображения...\nВероятность 98%: Это Кактус Маммиллярия.";
    }
}