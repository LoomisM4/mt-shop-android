package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.ImageArticle;
import de.mt.shop.objects.Link;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ArticleListAsyncTask extends AsyncTask<String, Integer, List<ImageArticle>> {
    private Consumer<List<ImageArticle>> onArticlesLoaded;

    public ArticleListAsyncTask(Consumer<List<ImageArticle>> onArticlesLoaded) {
        this.onArticlesLoaded = onArticlesLoaded;
    }

    @Override
    protected List<ImageArticle> doInBackground(String... strings) {
        Link link = new Link();
        link.setHref(strings[0]);
        List<Article> a = Api.articles(link);
        return a.stream().map(article -> {
            Bitmap image = Api.image(article.getLinks().getPreview());
            return new ImageArticle(article, image);
        }).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<ImageArticle> articles) {
        Optional.ofNullable(onArticlesLoaded).ifPresent(c -> c.accept(articles));
    }
}
