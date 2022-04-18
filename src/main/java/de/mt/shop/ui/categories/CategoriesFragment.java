package de.mt.shop.ui.categories;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import de.mt.shop.CategoriesArrayAdapter;
import de.mt.shop.databinding.FragmentCategoriesBinding;
import de.mt.shop.objects.Category;
import de.mt.shop.services.CategoriesAsyncTask;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    LayoutInflater inflater;
    private List<Category> entries;
    private CategoriesArrayAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        entries = new ArrayList<>();
        adapter = new CategoriesArrayAdapter(getContext(), android.R.layout.simple_list_item_1, entries);
        adapter.setOnItemTapped(this::onItemTapped);
        ListView list = binding.categoriesList;
        list.setAdapter(adapter);

        new CategoriesAsyncTask(adapter, entries).execute();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onItemTapped(Category c) {

    }
}