package de.mt.shop.objects;

import lombok.Data;

@Data
public class Category {
    private long categoryID;
    private String name;
    private Links links;
}
