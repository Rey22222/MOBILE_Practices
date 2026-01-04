package ru.mirea.khasanova.succuforest.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.khasanova.succuforest.data.storage.room.SucculentDao;
import ru.mirea.khasanova.succuforest.data.storage.room.SucculentEntity;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;
import ru.mirea.khasanova.succuforest.domain.repository.SucculentRepository;

public class SucculentRepositoryImpl implements SucculentRepository {
    private final ru.mirea.khasanova.data.network.MockNetworkApi api;
    private final SucculentDao dao;

    public SucculentRepositoryImpl(ru.mirea.khasanova.data.network.MockNetworkApi api, SucculentDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public List<Succulent> getCatalog() {
        if (dao.getAll().isEmpty()) {
            List<ru.mirea.khasanova.data.network.MockNetworkApi.Dto> net = api.fetchSucculents();
            List<SucculentEntity> entities = new ArrayList<>();
            for (ru.mirea.khasanova.data.network.MockNetworkApi.Dto d : net) {
                entities.add(new SucculentEntity(d.id, d.name, d.price, d.img, d.desc, false));
            }
            dao.insertAll(entities);
        }
        return map(dao.getAll());
    }

    @Override
    public List<Succulent> getFavorites() {
        return map(dao.getFavorites());
    }

    @Override
    public Succulent getById(int id) {
        SucculentEntity e = dao.getById(id);
        return e == null ? null : new Succulent(e.id, e.name, e.price, e.imageUrl, e.description, e.isFavorite);
    }

    @Override
    public void toggleFavorite(int id) {
        SucculentEntity e = dao.getById(id);
        if (e != null) dao.updateFavorite(id, !e.isFavorite);
    }
    @Override
    public String predictSucculentType() {
        return "Анализ завершен...\nРезультат: Эхеверия (99%)";
    }

    private List<Succulent> map(List<SucculentEntity> entities) {
        List<Succulent> res = new ArrayList<>();
        for (SucculentEntity e : entities) {
            res.add(new Succulent(e.id, e.name, e.price, e.imageUrl, e.description, e.isFavorite));
        }
        return res;
    }
}