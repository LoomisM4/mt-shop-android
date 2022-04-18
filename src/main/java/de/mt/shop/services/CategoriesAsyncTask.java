package de.mt.shop.services;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Consumer;

import de.mt.shop.objects.Category;
import de.mt.shop.objects.Link;

public class CategoriesAsyncTask extends AsyncTask<Link, Integer, List<Category>> {
    private ArrayAdapter<String> adapter;
    private List<Category> entries;
    private Consumer<Link> onCategorySelected;

    public CategoriesAsyncTask(ArrayAdapter<String> adapter, List<Category> entries) {
        this.adapter = adapter;
        this.entries = entries;
        //this.onCategorySelected = onCategorySelected;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<Category> doInBackground(Link... links) {
        Link l;
        if (links == null || links.length == 0) {
            l = null;
        } else {
            l = links[0];
        }
        return Api.categories(l);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(List<Category> categories) {
        entries.addAll(categories);
        adapter.notifyDataSetChanged();
    }
}
