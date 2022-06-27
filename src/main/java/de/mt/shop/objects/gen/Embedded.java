package de.mt.shop.objects.gen;

import java.util.List;

import lombok.Data;

@Data
public class Embedded {
    private List<Article> articles;
    private List<Category> categories;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}