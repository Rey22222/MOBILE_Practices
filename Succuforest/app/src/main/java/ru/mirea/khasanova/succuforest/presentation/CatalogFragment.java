package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.khasanova.succuforest.R;

public class CatalogFragment extends Fragment {

    private static final String TAG = "CatalogFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView вызван");
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated вызван");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        Log.d(TAG, "Инициализация CatalogViewModel");
        CatalogViewModel viewModel = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(CatalogViewModel.class);
        Log.d(TAG, "Начало наблюдения за списком суккулентов");
        viewModel.getSucculents().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                Log.d(TAG, "Данные получены успешно. Размер списка: " + list.size());
                SucculentAdapter adapter = new SucculentAdapter(list, getContext());
                recyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Список данных пуст");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView вызван: очистка ресурсов интерфейса");
    }
}