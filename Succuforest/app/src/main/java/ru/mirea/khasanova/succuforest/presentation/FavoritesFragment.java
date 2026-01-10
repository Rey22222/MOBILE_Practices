package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ru.mirea.khasanova.succuforest.R;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel vm;
    private SucculentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new SucculentAdapter(new ArrayList<>(), getContext());
        rv.setAdapter(adapter);

        vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(FavoritesViewModel.class);

        vm.getFavorites().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                adapter.updateData(list);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vm != null) {
            vm.refresh();
        }
    }
}