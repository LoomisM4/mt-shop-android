package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.Link;
import de.mt.shop.objects.SpotlightArticle;

public class SpotlightAsyncTask extends AsyncTask<Void, Integer, List<SpotlightArticle>> {
    private List<SpotlightArticle> spotlightArticles;
    private ArrayAdapter<SpotlightArticle> adapter;

    public SpotlightAsyncTask(List<SpotlightArticle> spotlightArticles, ArrayAdapter<SpotlightArticle> adapter) {
        this.spotlightArticles = spotlightArticles;
        this.adapter = adapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<SpotlightArticle> doInBackground(Void... voids) {
        List<Article> articles = Api.spotlight();
        return articles.stream().map(a -> {
            Link l = a.getLinks().getSpotlightImage();
            Bitmap img = Api.image(l);
            return new SpotlightArticle(a, img);
        }).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<SpotlightArticle> articles) {
        spotlightArticles.addAll(articles);
        adapter.notifyDataSetChanged();
    }
}
