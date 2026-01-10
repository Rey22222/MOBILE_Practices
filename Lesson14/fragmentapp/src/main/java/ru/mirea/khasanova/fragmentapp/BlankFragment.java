package ru.mirea.khasanova.fragmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BlankFragment extends Fragment {
    public BlankFragment() {
        super(R.layout.fragment_blank);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textViewStudentNumber);

        if (getArguments() != null) {
            int number = getArguments().getInt("my_number_student");
            textView.setText("Номер студента: " + number);
        }
    }
}