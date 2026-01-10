package ru.mirea.khasanova.fragmentmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class DetailsFragment extends Fragment {
    public DetailsFragment() { super(R.layout.fragment_details); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nameTv = view.findViewById(R.id.textViewCountryName);
        TextView descTv = view.findViewById(R.id.textViewDescription);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelectedCountry().observe(getViewLifecycleOwner(), country -> {
            if (country != null) {
                nameTv.setText(country.getName());
                descTv.setText(country.getDescription());
            }
        });
    }
}