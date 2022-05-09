package de.mt.shop.objects;

import android.graphics.Bitmap;

public class ImageArticle {
    private Bitmap img;
    private Article article;

    public ImageArticle(Article article, Bitmap bitmap) {
        this.article = article;
        this.img = bitmap;
    }

    public Bitmap getImg() {
        return img;
    }

    public Article getArticle() {
        return article;
    }
}
