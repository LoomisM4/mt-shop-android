package de.mt.shop.services;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.mt.shop.objects.Article;
import de.mt.shop.objects.Category;
import de.mt.shop.objects.Link;
import de.mt.shop.objects.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Api {
    private static SharedPreferences prefs;
    private static ConnectivityManager connectivityManager;
    private static String baseUrl = "https://shop.marcelwettach.eu";

    public static void init(SharedPreferences prefs, ConnectivityManager connectivityManager) {
        Api.prefs = prefs;
        Api.connectivityManager = connectivityManager;
    }

    public static List<Article> spotlight() {
        Optional<Response> response = webOrCache(baseUrl + "/spotlight", Response.class);
        return response.map(r -> r.getEmbedded().getArticles())
                .orElse(new ArrayList<>());
    }

    public static Article details(Link link) {
        Optional<Article> details = webOrCache(link.getHref(), Article.class);
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

    public static List<Article> articles(Link link) {
        Optional<Response> response = webOrCache(link.getHref(), Response.class);
        return response.map(r -> r.getEmbedded().getArticles())
                .orElse(new ArrayList<>());
    }

    public static Bitmap image(Link link) {
        if (isOnline()) {
            try {
                link.setHref(link.getHref().replaceFirst("http://", "https://"));
                URL url = new URL(link.getHref());
                InputStream stream = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                saveInCache(link.getHref(), base64);

                stream.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Optional<String> cachedResponse = readFromCache(link.getHref());
        return cachedResponse.map(r -> {
            byte[] bytes = Base64.decode(r, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }).orElse(null);
    }

    private static <T> Optional<T> webOrCache(String uri, Class<T> responseClass) {
        Gson gson = new Gson();
        if (isOnline()) {
            try {
                uri = uri.replaceFirst("http://", "https://");
                URL url = new URL(uri);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                T t = gson.fromJson(reader, responseClass);
                reader.close();
                saveInCache(uri, gson.toJson(t));
                return Optional.ofNullable(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Optional<String> cachedResponse = readFromCache(uri);
        return cachedResponse.map(r -> gson.fromJson(r, responseClass));
    }

    private static void saveInCache(String url, String response) {
        System.out.println("caching");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(url, response);
        editor.apply();
    }

    private static Optional<String> readFromCache(String url) {
        System.out.println("serving from cache");
        return Optional.ofNullable(prefs.getString(url, null));
    }

    private static boolean isOnline() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
