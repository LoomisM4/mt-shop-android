package de.mt.shop.objects;

import lombok.Data;

@Data
public class Article {
    private long id;
    private Links _links;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Links getLinks() {
        return _links;
    }

    public void setLinks(Links links) {
        this._links = links;
    }
}
