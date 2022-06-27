package de.mt.shop.ui.articlelist;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.mt.shop.R;
import de.mt.shop.databinding.FragmentArticlelistBinding;
import de.mt.shop.objects.ImageArticle;
import de.mt.shop.services.ArticleListAsyncTask;
import de.mt.shop.ui.adapters.BitmapArrayAdapter;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class ArticleListFragment extends Fragment { // 1
    private FragmentArticlelistBinding binding; // 0
    private List<ImageArticle> articles; // 0
    private BitmapArrayAdapter adapter; // 0

    public ArticleListFragment() { // 1
        // Required empty public constructor
    }

    @Override // 1
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // 1
        binding = FragmentArticlelistBinding.inflate(inflater, container, false); // 2
        View view = binding.getRoot(); // 2

        articles = new ArrayList<>(); // 2
        adapter = new BitmapArrayAdapter(getContext(), R.layout.spotlight_image, articles); // 5
        adapter.setOnItemClick(this::onArticleClicked); // 2
        binding.articleGrid.setAdapter(adapter); // 2

        Bundle args = getArguments(); // 2
        if (args != null) { // 2
            String url = args.getString("url"); // 2
            String title = args.getString("title"); // 2
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title); // 4
            new ArticleListAsyncTask(this::onArticlesLoaded).execute(url); // 3
        }

        return view; // 1
    }

    private void onArticlesLoaded(List<ImageArticle> articles) { // 1
        this.articles.addAll(articles); // 2
        adapter.notifyDataSetChanged(); // 1
    }

    private void onArticleClicked(ImageArticle imageArticle) { // 1
        if (imageArticle.getArticle().getLinks().getDetails() != null) { // 5
            Bundle args = new Bundle(); // 2
            args.putString("url", imageArticle.getArticle().getLinks().getDetails().getHref()); // 5
            args.putString("title", imageArticle.getArticle().getName()); // 3
            NavHostFragment.findNavController(this) // 1
                    .navigate(R.id.action_articleListFragment_to_detailsFragement, args); // 3
        }
    }
}

// 63