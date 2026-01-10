package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class CatalogFragment extends Fragment {
    private static final String TAG = "CatalogFragment";
    private SucculentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Создание разметки");
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: Инициализация списка");

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new SucculentAdapter(new ArrayList<Succulent>(), getContext());
        rv.setAdapter(adapter);

        CatalogViewModel vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(CatalogViewModel.class);

        Log.d(TAG, "Подписка на LiveData");
        vm.getSucculents().observe(getViewLifecycleOwner(), list -> {
            if (list != null && !list.isEmpty()) {
                Log.d(TAG, "Данные получены! Элементов: " + list.size());
                adapter.updateData(list);
            } else {
                Log.w(TAG, "Получен пустой список или null");
                Toast.makeText(getContext(), "Каталог пуст", Toast.LENGTH_SHORT).show();
            }
        });

        vm.loadCatalog();
    }
}