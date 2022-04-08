package de.mt.shop.objects;

import android.graphics.Bitmap;

public class SpotlightArticle {
    private Bitmap img;
    private Article article;

    public SpotlightArticle(Article article, Bitmap bitmap) {
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
