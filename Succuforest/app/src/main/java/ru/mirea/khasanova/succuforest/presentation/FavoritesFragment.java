package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class FavoritesFragment extends Fragment {
    private FavoritesViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(FavoritesViewModel.class);

        vm.getFavorites().observe(getViewLifecycleOwner(), list -> {
            rv.setAdapter(new SucculentAdapter(list, getContext()));
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vm != null) vm.refresh();
    }
}