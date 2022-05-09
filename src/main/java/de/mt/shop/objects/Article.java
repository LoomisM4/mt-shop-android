package de.mt.shop.objects;

import lombok.Data;

@Data
public class Article {
    private long id;
    private String name;
    private double price;
    private String description;
    private Links _links;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Links getLinks() {
        return _links;
    }

    public void setLinks(Links links) {
        this._links = links;
    }
}
