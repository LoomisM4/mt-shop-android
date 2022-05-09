package de.mt.shop.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Consumer;

import de.mt.shop.objects.Category;

public class CategoriesArrayAdapter extends ArrayAdapter {
    private List<Category> categories;
    private Consumer<Category> onItemTapped;

    public CategoriesArrayAdapter(@NonNull Context context, int resource, List<Category> entries) {
        super(context, resource, entries);
        this.categories = entries;
    }

    public void setOnItemTapped(Consumer<Category> onItemTapped) {
        this.onItemTapped = onItemTapped;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (onItemTapped != null) {
            view.setOnClickListener(v1 -> onItemTapped.accept(categories.get(position)));
        }

        return view;
    }
}
