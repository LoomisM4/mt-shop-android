package de.mt.shop.objects;

import android.graphics.Bitmap;

import de.mt.shop.objects.gen.Article;

public class ImageArticle { // 1
    private Bitmap img; // 0
    private Article article; // 0

    public ImageArticle(Article article, Bitmap bitmap) { // 1
        this.article = article; // 2
        this.img = bitmap; // 2
    }

    public Bitmap getImg() { // 1
        return img; // 1
    }

    public Article getArticle() { // 1
        return article; // 1
    }
}

// 10
