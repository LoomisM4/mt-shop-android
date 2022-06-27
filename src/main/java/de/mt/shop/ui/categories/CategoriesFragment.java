package de.mt.shop.ui.categories;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import de.mt.shop.R;
import de.mt.shop.objects.gen.Link;
import de.mt.shop.ui.adapters.CategoriesArrayAdapter;
import de.mt.shop.databinding.FragmentCategoriesBinding;
import de.mt.shop.objects.gen.Category;
import de.mt.shop.services.CategoriesAsyncTask;

public class CategoriesFragment extends Fragment { // 1

    private FragmentCategoriesBinding binding; // 0
    LayoutInflater inflater; // 0
    private List<Category> entries; // 0
    private CategoriesArrayAdapter adapter; // 0

    @RequiresApi(api = Build.VERSION_CODES.N) // 4
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { // 1
        this.inflater = inflater; // 2
        binding = FragmentCategoriesBinding.inflate(inflater, container, false); // 2
        View root = binding.getRoot(); // 2

        entries = new ArrayList<>(); // 2
        adapter = new CategoriesArrayAdapter(getContext(), android.R.layout.simple_list_item_1, entries); // 6
        adapter.setOnItemTapped(this::onItemTapped); // 2
        ListView list = binding.categoriesList; // 2
        list.setAdapter(adapter); // 1

        Bundle args = getArguments(); // 2
        Link link = null; // 1
        if (args != null) { // 2
            String url = args.getString("url"); // 2
            if (url != null) { // 2
                link = new Link(); // 2
                link.setHref(url); // 1
            }
            String title = args.getString("title"); // 2
            if (title != null) { // 2
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title); // 4
            }
        }

        new CategoriesAsyncTask(adapter, entries).execute(link); // 2

        return root; // 1
    }

    @Override // 1
    public void onDestroyView() { // 1
        super.onDestroyView(); // 1
        binding = null; // 1
    }

    public void onItemTapped(Category c) { // 1
        Bundle args = new Bundle(); // 2
        args.putString("title", c.getName()); // 2
        if (c.getLinks().getSubcategories() != null) { // 4
            args.putString("url", c.getLinks().getSubcategories().getHref()); // 4
            NavHostFragment.findNavController(this) // 1
                    .navigate(R.id.action_navigation_categories_self, args); // 3
        } else if (c.getLinks().getArticles() != null) { // 4
            args.putString("url", c.getLinks().getArticles().getHref()); // 4
            NavHostFragment.findNavController(this) // 1
                    .navigate(R.id.action_navigation_categories_to_articleListFragment, args); // 3
        }
    }
}

// 81