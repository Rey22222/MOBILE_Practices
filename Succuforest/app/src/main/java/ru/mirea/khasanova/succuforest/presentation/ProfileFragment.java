package ru.mirea.khasanova.succuforest.presentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.khasanova.succuforest.R;

public class ProfileFragment extends Fragment {
    public ProfileFragment() { super(R.layout.fragment_profile); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfileViewModel vm = new ViewModelProvider(this).get(ProfileViewModel.class);

        TextView emailTv = view.findViewById(R.id.tvProfileEmail);
        vm.getUserEmail().observe(getViewLifecycleOwner(), emailTv::setText);

        vm.getLogoutEvent().observe(getViewLifecycleOwner(), logout -> {
            if (logout) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> vm.logout());
    }
}