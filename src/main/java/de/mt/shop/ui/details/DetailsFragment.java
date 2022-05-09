package de.mt.shop.ui.details;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Optional;

import de.mt.shop.databinding.FragmentDetailsBinding;
import de.mt.shop.objects.Article;
import de.mt.shop.objects.Cart;
import de.mt.shop.services.DetailsAsyncTask;
import de.mt.shop.services.ImageAsyncTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailsFragment extends Fragment {
    private String url;
    private String title;
    private FragmentDetailsBinding binding;
    private LayoutInflater inflater;
    private Article article;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.url = args.getString("url");
        this.title = args.getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.addToCartButton.setOnClickListener(this::onAddToCartClicked);

        new DetailsAsyncTask(this::onSuccess).execute(url);

        return view;
    }

    public void onAddToCartClicked(View view) {
        Optional.ofNullable(article).ifPresent(d -> {
            Cart.shared().addArticle(article);
        });
    }

    private void onSuccess(Article article) {
        article.getLinks().getImages().forEach(i -> {
            new ImageAsyncTask(this::onImageLoaded).execute(i);
        });
        binding.titleText.setText(article.getName());
        binding.descriptionText.setText(article.getDescription());
        this.article = article;
    }

    private void onImageLoaded(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        binding.imagesLayout.addView(imageView);
    }
}