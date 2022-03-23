package de.mt.shop.objects;

import lombok.Data;

@Data
public class Details {
    private long id;
    private String name;
    private double price;
    private String description;
    private Links links;
}
