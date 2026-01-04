package ru.mirea.khasanova.succuforest.presentation;

import android.content.Intent;
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

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.data.repository.SucculentRepositoryImpl;
import ru.mirea.khasanova.succuforest.data.storage.room.AppDatabase;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;
import ru.mirea.khasanova.succuforest.domain.usecases.DetermineSucculentTypeUseCase;
import ru.mirea.khasanova.succuforest.domain.usecases.GetSucculentDetailsUseCase;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SucculentRepositoryImpl repository = new SucculentRepositoryImpl(
                new ru.mirea.khasanova.data.network.MockNetworkApi(),
                AppDatabase.getInstance(requireContext()).dao()
        );
        GetSucculentDetailsUseCase detailsUseCase = new GetSucculentDetailsUseCase(repository);
        DetermineSucculentTypeUseCase predictUseCase = new DetermineSucculentTypeUseCase(repository);

        EditText etId = view.findViewById(R.id.etSucculentId);
        TextView tvInfo = view.findViewById(R.id.tvInfo);
        Button btnDetails = view.findViewById(R.id.btnDetails);
        Button btnPredict = view.findViewById(R.id.btnPredict);
        btnDetails.setOnClickListener(v -> {
            String idStr = etId.getText().toString();
            if (!idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Succulent s = detailsUseCase.execute(id);
                    if (s != null) {
                        tvInfo.setText(s.getName() + " - " + s.getPrice());
                    } else {
                        tvInfo.setText("Не найдено");
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка ввода", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPredict.setOnClickListener(v -> {
            String result = predictUseCase.execute();
            tvInfo.setText(result);
        });
    }
}