package de.mt.shop.objects;

import lombok.Data;

@Data
public class Response {
    public Embedded getEmbedded() {
        return _embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this._embedded = embedded;
    }

    private Embedded _embedded;
}