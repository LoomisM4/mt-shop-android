package de.mt.shop.services;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.util.List;

import de.mt.shop.objects.gen.Category;
import de.mt.shop.objects.gen.Link;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class CategoriesAsyncTask extends AsyncTask<Link, Integer, List<Category>> { // 1
    private ArrayAdapter<String> adapter; // 0
    private List<Category> entries; // 0

    public CategoriesAsyncTask(ArrayAdapter<String> adapter, List<Category> entries) { // 1
        this.adapter = adapter; // 2
        this.entries = entries; // 2
    }

    @Override // 1
    protected List<Category> doInBackground(Link... links) { // 1
        Link l; // 0
        if (links == null || links.length == 0) { // 5
            l = null; // 1
        } else { // 1
            l = links[0]; // 2
        }
        return Api.categories(l); // 2
    }

    @Override // 1
    protected void onPostExecute(List<Category> categories) { // 1
        entries.addAll(categories); // 1
        adapter.notifyDataSetChanged(); // 1
    }
}

// 27