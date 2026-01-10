package ru.mirea.khasanova.fragmentmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    public ListFragment() { super(R.layout.fragment_list); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        ListView listView = view.findViewById(R.id.listView);
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("Россия", "Самая большая страна в мире, расположенная в Европе и Азии."));
        countries.add(new Country("Бразилия", "Крупнейшее государство Южной Америки, знаменитое карнавалами и футболом."));
        countries.add(new Country("Китай", "Страна с древнейшей историей и самым большим населением в мире."));
        countries.add(new Country("Индия", "Яркая страна в Южной Азии, родина йоги и специй."));
        countries.add(new Country("ЮАР", "Самая развитая страна Африки, известная своими национальными парками."));
        List<String> names = new ArrayList<>();
        for (Country c : countries) names.add(c.getName());

        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names));

        listView.setOnItemClickListener((parent, v, position, id) -> {
            viewModel.selectCountry(countries.get(position));
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DetailsFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}