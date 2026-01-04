package ru.mirea.khasanova.succuforest.presentation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ru.mirea.khasanova.succuforest.R;

public class RootActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setOnItemSelectedListener(item -> {
            Fragment f = null;
            if (item.getItemId() == R.id.nav_home) f = new HomeFragment();
            else if (item.getItemId() == R.id.nav_catalog) f = new CatalogFragment();
            else if (item.getItemId() == R.id.nav_fav) f = new FavoritesFragment();

            if (f != null) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
            return true;
        });
        // Default
        if (savedInstanceState == null) nav.setSelectedItemId(R.id.nav_home);
    }
}