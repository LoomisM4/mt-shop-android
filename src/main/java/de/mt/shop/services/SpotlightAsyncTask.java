package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.Link;
import de.mt.shop.objects.ImageArticle;

public class SpotlightAsyncTask extends AsyncTask<Void, Integer, List<ImageArticle>> {
    private List<ImageArticle> imageArticles;
    private ArrayAdapter<ImageArticle> adapter;

    public SpotlightAsyncTask(List<ImageArticle> imageArticles, ArrayAdapter<ImageArticle> adapter) {
        this.imageArticles = imageArticles;
        this.adapter = adapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<ImageArticle> doInBackground(Void... voids) {
        List<Article> articles = Api.spotlight();
        return articles.stream().map(a -> {
            Link l = a.getLinks().getSpotlightImage();
            Bitmap img = Api.image(l);
            return new ImageArticle(a, img);
        }).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<ImageArticle> articles) {
        imageArticles.addAll(articles);
        adapter.notifyDataSetChanged();
    }
}
