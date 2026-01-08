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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView вызван");

        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated вызван");

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Log.d(TAG, "Инициализация CatalogViewModel через фабрику");
        CatalogViewModel vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(CatalogViewModel.class);

        Log.d(TAG, "Запрос списка суккулентов...");
        vm.getSucculents().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                Log.d(TAG, "Данные получены успешно. Количество элементов: " + list.size());
            } else {
                Log.d(TAG, "Список суккулентов пуст или null");
            }
            rv.setAdapter(new SucculentAdapter(list, getContext()));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView вызван");
    }
}