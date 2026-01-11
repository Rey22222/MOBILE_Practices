package ru.mirea.khasanova.navigationdrawerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import ru.mirea.khasanova.navigationdrawerapp.databinding.FragmentGalleryBinding;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        binding.recyclerViewGallery.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<Plant> plantList = new ArrayList<>();
        plantList.add(new Plant(R.drawable.succulent_1, "Echeveria Roca"));
        plantList.add(new Plant(R.drawable.succulent_2, "Adenia Glauca"));
        plantList.add(new Plant(R.drawable.succulent_3, "Eriospermum Dregei"));
        plantList.add(new Plant(R.drawable.succulent_4, "Ariocarpus Asuka"));
        GalleryAdapter adapter = new GalleryAdapter(plantList);
        binding.recyclerViewGallery.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}