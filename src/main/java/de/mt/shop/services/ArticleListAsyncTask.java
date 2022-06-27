package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import de.mt.shop.objects.gen.Article;
import de.mt.shop.objects.ImageArticle;
import de.mt.shop.objects.gen.Link;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class ArticleListAsyncTask extends AsyncTask<String, Integer, List<ImageArticle>> { // 1
    private Consumer<List<ImageArticle>> onArticlesLoaded; // 0

    public ArticleListAsyncTask(Consumer<List<ImageArticle>> onArticlesLoaded) { // 1
        this.onArticlesLoaded = onArticlesLoaded; // 2
    }

    @Override // 1
    protected List<ImageArticle> doInBackground(String... strings) { // 1
        Link link = new Link(); // 2
        link.setHref(strings[0]); // 2
        List<Article> a = Api.articles(link); // 2
        return a.stream().map(article -> { // 3
            Bitmap image = Api.image(article.getLinks().getPreview()); // 4
            return new ImageArticle(article, image); // 2
        }).collect(Collectors.toList()); // 2
    }

    @Override // 1
    protected void onPostExecute(List<ImageArticle> articles) { // 1
        Optional.ofNullable(onArticlesLoaded).ifPresent(c -> c.accept(articles)); // 3
    }
}

// 32