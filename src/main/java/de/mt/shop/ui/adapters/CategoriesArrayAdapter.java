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

import de.mt.shop.objects.gen.Category;

public class CategoriesArrayAdapter extends ArrayAdapter { // 1
    private List<Category> categories; // 0
    private Consumer<Category> onItemTapped; // 0

    public CategoriesArrayAdapter(@NonNull Context context, int resource, List<Category> entries) { // 1
        super(context, resource, entries); // 1
        this.categories = entries; // 2
    }

    public void setOnItemTapped(Consumer<Category> onItemTapped) { // 1
        this.onItemTapped = onItemTapped; // 2
    }

    @RequiresApi(api = Build.VERSION_CODES.N) // 4
    @NonNull // 1
    @Override // 1
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { // 1
        View view = super.getView(position, convertView, parent); // 2

        if (onItemTapped != null) { // 2
            view.setOnClickListener(v1 -> onItemTapped.accept(categories.get(position))); // 3
        }

        return view; // 1
    }
}

// 23