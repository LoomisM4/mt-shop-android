package de.mt.shop.objects.gen;

import java.util.List;

import lombok.Data;

@Data
public class Links {
    private Link spotlightImage;
    private Link details;
    private Link preview;
    private Link subcategories;

    public Link getSpotlightImage() {
        return spotlightImage;
    }

    public void setSpotlightImage(Link spotlightImage) {
        this.spotlightImage = spotlightImage;
    }

    public Link getDetails() {
        return details;
    }

    public void setDetails(Link details) {
        this.details = details;
    }

    public Link getPreview() {
        return preview;
    }

    public void setPreview(Link preview) {
        this.preview = preview;
    }

    public Link getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Link subcategories) {
        this.subcategories = subcategories;
    }

    public Link getArticles() {
        return articles;
    }

    public void setArticles(Link articles) {
        this.articles = articles;
    }

    public List<Link> getImages() {
        return images;
    }

    public void setImages(List<Link> images) {
        this.images = images;
    }

    private Link articles;
    private List<Link> images;
}
