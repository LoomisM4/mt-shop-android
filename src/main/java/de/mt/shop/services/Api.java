package de.mt.shop.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.Category;
import de.mt.shop.objects.Details;
import de.mt.shop.objects.Link;
import de.mt.shop.objects.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
class Api {
    static String baseUrl = "https://shop.marcelwettach.eu";

    public static List<Article> spotlight() {
        Optional<Response> response = webOrCache(baseUrl + "/spotlight", Response.class);
        return response.map(r -> r.getEmbedded().getArticles())
                .orElse(new ArrayList<>());
    }

    public static Details details(Link link) {
        Optional<Details> details = webOrCache(link.getHref(), Details.class);
        return details.orElse(null);
    }

    public static List<Category> categories(Link link) {
        Link l = Optional.ofNullable(link).orElseGet(() -> {
            Link linkTemp = new Link();
            linkTemp.setHref(baseUrl + "/categories");
            return linkTemp;
        });

        Optional<Response> response = webOrCache(l.getHref(), Response.class);
        return response.map(r -> r.getEmbedded().getCategories())
                .orElse(new ArrayList<>());
    }

    public static Bitmap image(Link link) {
        try {
            link.setHref(link.getHref().replaceFirst("http://", "https://")); // TODO sollte wieder weg
            URL url = new URL(link.getHref());
            InputStream stream = url.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> Optional<T> webOrCache(String uri, Class<T> responseClass) {
        try {
            URL url = new URL(uri);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            T t = new Gson().fromJson(reader, responseClass);
            reader.close();
            return Optional.ofNullable(t);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
