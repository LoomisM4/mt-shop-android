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

@RequiresApi(api = Build.VERSION_CODES.N)
public class ArticleListFragment extends Fragment {
    private FragmentArticlelistBinding binding;
    private List<ImageArticle> articles;
    private BitmapArrayAdapter adapter;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlelistBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        articles = new ArrayList<>();
        adapter = new BitmapArrayAdapter(getContext(), R.layout.spotlight_image, articles);
        adapter.setOnItemClick(this::onArticleClicked);
        binding.articleGrid.setAdapter(adapter);

        Bundle args = getArguments();
        if (args != null) {
            String url = args.getString("url");
            String title = args.getString("title");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
            new ArticleListAsyncTask(this::onArticlesLoaded).execute(url);
        }

        return view;
    }

    private void onArticlesLoaded(List<ImageArticle> articles) {
        this.articles.addAll(articles);
        adapter.notifyDataSetChanged();
    }

    private void onArticleClicked(ImageArticle imageArticle) {
        if (imageArticle.getArticle().getLinks().getDetails() != null) {
            Bundle args = new Bundle();
            args.putString("url", imageArticle.getArticle().getLinks().getDetails().getHref());
            args.putString("title", imageArticle.getArticle().getName());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_articleListFragment_to_detailsFragement, args);
        }
    }
}