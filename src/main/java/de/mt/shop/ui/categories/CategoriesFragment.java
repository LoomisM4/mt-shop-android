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
import de.mt.shop.objects.Link;
import de.mt.shop.ui.adapters.CategoriesArrayAdapter;
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

        Bundle args = getArguments();
        Link link = null;
        if (args != null) {
            String url = args.getString("url");
            if (url != null) {
                link = new Link();
                link.setHref(url);
            }
            String title = args.getString("title");
            if (title != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
            }
        }

        new CategoriesAsyncTask(adapter, entries).execute(link);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onItemTapped(Category c) {
        Bundle args = new Bundle();
        args.putString("title", c.getName());
        if (c.getLinks().getSubcategories() != null) {
            args.putString("url", c.getLinks().getSubcategories().getHref());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_categories_self, args);
        } else if (c.getLinks().getArticles() != null) {
            args.putString("url", c.getLinks().getArticles().getHref());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_categories_to_articleListFragment, args);
        }
    }
}