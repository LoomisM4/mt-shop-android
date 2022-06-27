package de.mt.shop.services;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;
import java.util.function.Consumer;

import de.mt.shop.objects.gen.Article;
import de.mt.shop.objects.gen.Link;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class DetailsAsyncTask extends AsyncTask<String, Integer, Article> { // 1
    private Consumer<Article> onSuccess; // 0

    public DetailsAsyncTask(Consumer<Article> onSuccess) { // 1
        this.onSuccess = onSuccess; // 2
    }

    @Override // 1
    protected Article doInBackground(String... strings) { // 1
        Link link = new Link(); // 2
        link.setHref(strings[0]); // 2
        return Api.details(link); // 2
    }

    @Override // 1
    protected void onPostExecute(Article article) { // 1
        Optional.ofNullable(onSuccess).ifPresent(c -> c.accept(article)); // 3
    }
}

// 21