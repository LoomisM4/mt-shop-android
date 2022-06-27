package de.mt.shop.ui.details;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Optional;

import de.mt.shop.databinding.FragmentDetailsBinding;
import de.mt.shop.objects.gen.Article;
import de.mt.shop.objects.Cart;
import de.mt.shop.services.DetailsAsyncTask;
import de.mt.shop.services.ImageAsyncTask;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class DetailsFragment extends Fragment { // 1
    private FragmentDetailsBinding binding; // 0
    private Article article; // 0

    public DetailsFragment() { // 1
        // Required empty public constructor
    }

    @Override // 1
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // 1
        binding = FragmentDetailsBinding.inflate(inflater, container, false); // 2
        View view = binding.getRoot(); // 2
        binding.addToCartButton.setOnClickListener(this::onAddToCartClicked); // 3

        Bundle args = getArguments(); // 2
        if (args != null) { // 2
            String url = args.getString("url"); // 2
            String title = args.getString("title"); // 2
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title); // 4
            new DetailsAsyncTask(this::onSuccess).execute(url); // 3
        }

        return view; // 1
    }

    public void onAddToCartClicked(View view) { // 1
        Optional.ofNullable(article).ifPresent(d -> { // 2
            Cart.shared().addArticle(article); // 2
        });
    }

    private void onSuccess(Article article) { // 1
        if (article == null) return; // 3

        article.getLinks().getImages().forEach(i -> { // 3
            new ImageAsyncTask(this::onImageLoaded).execute(i); // 3
        });
        binding.titleText.setText(article.getName()); // 3
        binding.descriptionText.setText(article.getDescription()); // 3
        this.article = article; // 2
    }

    private void onImageLoaded(Bitmap bitmap) { // 1
        ImageView imageView = new ImageView(getContext()); // 3
        imageView.setImageBitmap(bitmap); // 1
        binding.imagesLayout.addView(imageView); // 2
    }
}

// 61