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

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class BitmapArrayAdapter extends ArrayAdapter<ImageArticle> { // 1
    private List<ImageArticle> articles; // 0
    private Consumer<ImageArticle> onItemClick; // 0

    public BitmapArrayAdapter(@NonNull Context context, int resource, @NonNull List<ImageArticle> objects) { // 1
        super(context, resource, objects); // 1
        this.articles = objects; // 2
    }

    public void setOnItemClick(Consumer<ImageArticle> onItemClick) { // 1
        this.onItemClick = onItemClick; // 2
    }

    @NonNull // 1
    @Override // 1
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { // 1
        View v = convertView; // 1
        if (v == null) { // 2
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 5
            v = vi.inflate(R.layout.spotlight_image, null); // 4
        }

        ImageView view = v.findViewById(R.id.spotlight_image_view); // 4
        if (this.onItemClick != null) { // 3
            view.setOnClickListener(v1 -> onItemClick.accept(articles.get(position))); // 3
        }
        Bitmap img = articles.get(position).getImg(); // 3
        view.setImageBitmap(img); // 1
        return v; // 1
    }
}

// 42