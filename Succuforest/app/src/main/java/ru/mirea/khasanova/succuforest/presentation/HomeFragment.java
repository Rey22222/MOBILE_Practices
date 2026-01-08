package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.khasanova.succuforest.R;

public class HomeFragment extends Fragment {

    private HomeViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(this, new SuccuViewModelFactory(requireContext()))
                .get(HomeViewModel.class);

        EditText etId = view.findViewById(R.id.etSucculentId);
        TextView tvInfo = view.findViewById(R.id.tvInfo);
        Button btnDetails = view.findViewById(R.id.btnDetails);
        Button btnPredict = view.findViewById(R.id.btnPredict);

        vm.getScreenStatus().observe(getViewLifecycleOwner(), text -> {
            tvInfo.setText(text);
        });

        btnDetails.setOnClickListener(v -> {
            String idStr = etId.getText().toString();
            if (!idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    vm.searchById(id);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Введите число", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Поле пустое", Toast.LENGTH_SHORT).show();
            }
        });

        btnPredict.setOnClickListener(v -> {
            vm.predictType();
        });
    }
}