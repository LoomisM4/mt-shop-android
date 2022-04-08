package de.mt.shop.ui.spotlight;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import de.mt.shop.R;
import de.mt.shop.databinding.FragmentSpotlightBinding;
import de.mt.shop.objects.SpotlightArticle;
import de.mt.shop.services.SpotlightAsyncTask;
import de.mt.shop.ui.BitmapArrayAdapter;

public class SpotlightFragment extends Fragment {

    private FragmentSpotlightBinding binding;
    private List<SpotlightArticle> spotlightArticles;
    LayoutInflater inflater;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        binding = FragmentSpotlightBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spotlightArticles = new ArrayList<>();
        ListView spotlightList = binding.spotlightList;
        BitmapArrayAdapter adapter =
                new BitmapArrayAdapter(getContext(), R.layout.spotlight_image, spotlightArticles);
        adapter.setOnItemClick(this::onItemClicked);
        spotlightList.setAdapter(adapter);

        new SpotlightAsyncTask(spotlightArticles, adapter).execute();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onItemClicked(SpotlightArticle spotlightArticle) {
        //
    }
}