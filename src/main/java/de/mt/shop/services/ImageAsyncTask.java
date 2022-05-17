package de.mt.shop.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.view.Window;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;

import java.util.Optional;
import java.util.function.Consumer;

import de.mt.shop.objects.Link;

public class ImageAsyncTask extends AsyncTask<Link, Integer, Bitmap> {
    private Consumer<Bitmap> onImageLoaded;

    public ImageAsyncTask(Consumer<Bitmap> onImageLoaded) {
        this.onImageLoaded = onImageLoaded;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Bitmap doInBackground(Link... links) {
        return Api.image(links[0]);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Optional.ofNullable(onImageLoaded).ifPresent(c -> c.accept(bitmap));
    }
}
