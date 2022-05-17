package de.mt.shop.ui.details;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Optional;

import de.mt.shop.databinding.FragmentDetailsBinding;
import de.mt.shop.objects.Article;
import de.mt.shop.objects.Cart;
import de.mt.shop.services.DetailsAsyncTask;
import de.mt.shop.services.ImageAsyncTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;
    private LayoutInflater inflater;
    private Article article;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.addToCartButton.setOnClickListener(this::onAddToCartClicked);

        Bundle args = getArguments();
        if (args != null) {
            String url = args.getString("url");
            String title = args.getString("title");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
            new DetailsAsyncTask(this::onSuccess).execute(url);
        }

        return view;
    }

    public void onAddToCartClicked(View view) {
        Optional.ofNullable(article).ifPresent(d -> {
            Cart.shared().addArticle(article);
        });
    }

    private void onSuccess(Article article) {
        if (article == null) return;

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