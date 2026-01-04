package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentCatalogUseCase;

public class CatalogFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        GetSucculentCatalogUseCase useCase = new GetSucculentCatalogUseCase(new SucculentRepositoryImpl(
                new ru.mirea.khasanova.data.network.MockNetworkApi(), AppDatabase.getInstance(requireContext()).dao()));

        rv.setAdapter(new SucculentAdapter(useCase.getCatalog(), requireContext()));
        return view;
    }
}