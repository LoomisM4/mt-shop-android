package de.mt.shop.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Consumer;

import de.mt.shop.R;
import de.mt.shop.objects.ImageArticle;

public class BitmapArrayAdapter extends ArrayAdapter<ImageArticle> {
    private List<ImageArticle> articles;
    private Consumer<ImageArticle> onItemClick;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public BitmapArrayAdapter(@NonNull Context context, int resource, @NonNull List<ImageArticle> objects) {
        super(context, resource, objects);
        this.articles = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOnItemClick(Consumer<ImageArticle> onItemClick) {
        this.onItemClick = onItemClick;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.spotlight_image, null);
        }

        ImageView view = v.findViewById(R.id.spotlight_image_view);
        if (this.onItemClick != null) {
            view.setOnClickListener(v1 -> onItemClick.accept(articles.get(position)));
        }
        Bitmap img = articles.get(position).getImg();
        view.setImageBitmap(img);
        return v;
    }
}
