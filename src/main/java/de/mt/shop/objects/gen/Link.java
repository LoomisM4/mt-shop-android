package de.mt.shop.objects.gen;

import lombok.Data;

@Data
public class Link {
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
