package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

import de.mt.shop.objects.gen.Article;
import de.mt.shop.objects.gen.Link;
import de.mt.shop.objects.ImageArticle;

public class SpotlightAsyncTask extends AsyncTask<Void, Integer, List<ImageArticle>> { // 1
    private List<ImageArticle> imageArticles; // 0
    private ArrayAdapter<ImageArticle> adapter; // 0

    public SpotlightAsyncTask(List<ImageArticle> imageArticles, ArrayAdapter<ImageArticle> adapter) { // 1
        this.imageArticles = imageArticles; // 2
        this.adapter = adapter; // 2
    }

    @RequiresApi(api = Build.VERSION_CODES.N) // 4
    @Override // 1
    protected List<ImageArticle> doInBackground(Void... voids) { // 1
        List<Article> articles = Api.spotlight(); // 2
        return articles.stream().map(a -> { // 3
            Link l = a.getLinks().getSpotlightImage(); // 3
            Bitmap img = Api.image(l); // 2
            return new ImageArticle(a, img); // 2
        }).collect(Collectors.toList()); // 2
    }

    @Override // 1
    protected void onPostExecute(List<ImageArticle> articles) { // 1
        imageArticles.addAll(articles); // 1
        adapter.notifyDataSetChanged(); // 1
    }
}

// 30