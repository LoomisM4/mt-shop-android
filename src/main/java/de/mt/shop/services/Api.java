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

import de.mt.shop.objects.gen.Article;
import de.mt.shop.objects.gen.Category;
import de.mt.shop.objects.gen.Link;
import de.mt.shop.objects.gen.Response;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class Api { // 1
    private static SharedPreferences prefs; // 0
    private static ConnectivityManager connectivityManager; // 0
    private static String baseUrl = "https://shop.marcelwettach.eu"; // 1

    public static void init(SharedPreferences prefs, ConnectivityManager connectivityManager) { // 1
        Api.prefs = prefs; // 2
        Api.connectivityManager = connectivityManager; // 2
    }

    public static List<Article> spotlight() { // 1
        Optional<Response> response = webOrCache(baseUrl + "/spotlight", Response.class); // 4
        return response.map(r -> r.getEmbedded().getArticles()) // 4
                .orElse(new ArrayList<>()); // 2
    }

    public static Article details(Link link) { // 1
        Optional<Article> details = webOrCache(link.getHref(), Article.class); // 4
        return details.orElse(null); // 2
    }

    public static List<Category> categories(Link link) { // 1
        Link l = Optional.ofNullable(link).orElseGet(() -> { // 3
            Link linkTemp = new Link(); // 2
            linkTemp.setHref(baseUrl + "/categories"); // 2
            return linkTemp; // 1
        });

        Optional<Response> response = webOrCache(l.getHref(), Response.class); // 4
        return response.map(r -> r.getEmbedded().getCategories()) // 4
                .orElse(new ArrayList<>()); // 2
    }

    public static List<Article> articles(Link link) { // 1
        Optional<Response> response = webOrCache(link.getHref(), Response.class); // 4
        return response.map(r -> r.getEmbedded().getArticles()) // 4
                .orElse(new ArrayList<>()); // 2
    }

    public static Bitmap image(Link link) { // 1
        if (isOnline()) { // 2
            try { // 1
                link.setHref(link.getHref().replaceFirst("http://", "https://")); // 0
                URL url = new URL(link.getHref()); // 3
                InputStream stream = url.openStream(); // 2
                Bitmap bitmap = BitmapFactory.decodeStream(stream); // 2

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 2
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // 3
                byte[] byteArray = byteArrayOutputStream.toByteArray(); // 2
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT); // 3
                saveInCache(link.getHref(), base64); // 2

                stream.close(); // 1
                return bitmap; // 1
            } catch (IOException e) { // 1
                e.printStackTrace(); // 0
            }
        }

        Optional<String> cachedResponse = readFromCache(link.getHref()); // 3
        return cachedResponse.map(r -> { // 2
            byte[] bytes = Base64.decode(r, Base64.DEFAULT); // 3
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length); // 3
        }).orElse(null); // 1
    }

    private static <T> Optional<T> webOrCache(String uri, Class<T> responseClass) { // 1
        Gson gson = new Gson(); // 2
        if (isOnline()) { // 2
            try { // 1
                uri = uri.replaceFirst("http://", "https://"); // 0
                URL url = new URL(uri); // 2
                InputStreamReader reader = new InputStreamReader(url.openStream()); // 3
                T t = gson.fromJson(reader, responseClass); // 2
                reader.close(); // 1
                saveInCache(uri, gson.toJson(t)); // 2
                return Optional.ofNullable(t); // 2
            } catch (IOException e) { // 1
                e.printStackTrace(); // 0
            }
        }

        Optional<String> cachedResponse = readFromCache(uri); // 2
        return cachedResponse.map(r -> gson.fromJson(r, responseClass)); // 3
    }

    private static void saveInCache(String url, String response) { // 1
        System.out.println("caching"); // 0
        SharedPreferences.Editor editor = prefs.edit(); // 2
        editor.putString(url, response); // 1
        editor.apply(); // 1
    }

    private static Optional<String> readFromCache(String url) { // 1
        System.out.println("serving from cache"); // 0
        return Optional.ofNullable(prefs.getString(url, null)); // 3
    }

    private static boolean isOnline() { // 1
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo(); // 2
        return activeNetworkInfo != null && activeNetworkInfo.isConnected(); // 4
    }
}

// 137