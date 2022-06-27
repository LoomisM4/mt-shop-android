package de.mt.shop.ui.spotlight;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import de.mt.shop.R;
import de.mt.shop.databinding.FragmentSpotlightBinding;
import de.mt.shop.objects.ImageArticle;
import de.mt.shop.services.SpotlightAsyncTask;
import de.mt.shop.ui.adapters.BitmapArrayAdapter;

public class SpotlightFragment extends Fragment { // 1

    private FragmentSpotlightBinding binding; // 0
    private List<ImageArticle> imageArticles; // 0
    LayoutInflater inflater; // 0

    @RequiresApi(api = Build.VERSION_CODES.N) // 4
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { // 1

        this.inflater = inflater; // 2
        binding = FragmentSpotlightBinding.inflate(inflater, container, false); // 2
        View root = binding.getRoot(); // 2

        imageArticles = new ArrayList<>(); // 2
        ListView spotlightList = binding.spotlightList; // 2
        BitmapArrayAdapter adapter =
                new BitmapArrayAdapter(getContext(), R.layout.spotlight_image, imageArticles); // 5
        adapter.setOnItemClick(this::onItemClicked); // 2
        spotlightList.setAdapter(adapter); // 1

        new SpotlightAsyncTask(imageArticles, adapter).execute(); // 2

        return root; // 1
    }

    @Override // 1
    public void onDestroyView() { // 1
        super.onDestroyView(); // 1
        binding = null; // 1
    }

    private void onItemClicked(ImageArticle imageArticle) { // 1
        if (imageArticle.getArticle().getLinks().getDetails() != null) { // 5
            Bundle args = new Bundle(); // 2
            args.putString("url", imageArticle.getArticle().getLinks().getDetails().getHref()); // 5
            NavHostFragment.findNavController(this) // 1
                    .navigate(R.id.action_navigation_spotlight_to_detailsFragement, args); // 3
        }
    }
}

// 48