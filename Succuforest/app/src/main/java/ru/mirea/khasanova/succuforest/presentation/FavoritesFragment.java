package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        SucculentRepositoryImpl repository = new SucculentRepositoryImpl(
                new ru.mirea.khasanova.data.network.MockNetworkApi(),
                AppDatabase.getInstance(requireContext()).dao()
        );

        List<Succulent> favorites = repository.getFavorites();

        rv.setAdapter(new SucculentAdapter(favorites, getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView rv = getView().findViewById(R.id.recyclerView);
        if (rv != null) {
            SucculentRepositoryImpl repository = new SucculentRepositoryImpl(
                    new ru.mirea.khasanova.data.network.MockNetworkApi(),
                    AppDatabase.getInstance(requireContext()).dao()
            );
            rv.setAdapter(new SucculentAdapter(repository.getFavorites(), getContext()));
        }
    }
}