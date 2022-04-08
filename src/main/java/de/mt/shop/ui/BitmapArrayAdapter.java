package de.mt.shop.ui;

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
import java.util.function.Function;

import de.mt.shop.R;
import de.mt.shop.objects.SpotlightArticle;

public class BitmapArrayAdapter extends ArrayAdapter<SpotlightArticle> {
    private List<SpotlightArticle> articles;
    private Consumer<SpotlightArticle> onItemClick;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public BitmapArrayAdapter(@NonNull Context context, int resource, @NonNull List<SpotlightArticle> objects) {
        super(context, resource, objects);
        this.articles = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOnItemClick(Consumer<SpotlightArticle> onItemClick) {
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
