package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;
import java.util.function.Consumer;

import de.mt.shop.objects.gen.Link;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class ImageAsyncTask extends AsyncTask<Link, Integer, Bitmap> { // 1
    private Consumer<Bitmap> onImageLoaded; // 0

    public ImageAsyncTask(Consumer<Bitmap> onImageLoaded) { // 1
        this.onImageLoaded = onImageLoaded; // 2
    }

    @Override // 1
    protected Bitmap doInBackground(Link... links) { // 1
        return Api.image(links[0]); // 3
    }

    @Override // 1
    protected void onPostExecute(Bitmap bitmap) { // 1
        Optional.ofNullable(onImageLoaded).ifPresent(c -> c.accept(bitmap)); // 3
    }
}

// 18
