package de.mt.shop.services;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;
import java.util.function.Consumer;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.Link;

public class DetailsAsyncTask extends AsyncTask<String, Integer, Article> {
    private Consumer<Article> onSuccess;

    public DetailsAsyncTask(Consumer<Article> onSuccess) {
        this.onSuccess = onSuccess;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Article doInBackground(String... strings) {
        Link link = new Link();
        link.setHref(strings[0]);
        return Api.details(link);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Article article) {
        Optional.ofNullable(onSuccess).ifPresent(c -> c.accept(article));
    }
}
