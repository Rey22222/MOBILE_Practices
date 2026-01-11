package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.databinding.ActivityMainBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel vm;
    private ActivityMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(HomeViewModel.class);

        vm.getScreenStatus().observe(getViewLifecycleOwner(), text -> {
            binding.tvInfo.setText(text);
        });

        vm.getWeatherText().observe(getViewLifecycleOwner(), text -> {
            binding.tvWeatherInfo.setText(text);
        });

        vm.getWateringAdvice().observe(getViewLifecycleOwner(), advice -> {
            binding.tvWateringAdvice.setText(advice);
        });

        binding.btnDetails.setOnClickListener(v -> {
            String idStr = binding.etSucculentId.getText().toString();
            if (!idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    vm.searchById(id);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Поле ID пустое", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnPredict.setOnClickListener(v -> {
            vm.predictType();
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}