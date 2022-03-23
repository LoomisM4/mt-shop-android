package de.mt.shop.objects;

import java.util.List;

import lombok.Data;

@Data
public class Embedded {
    private List<Article> articles;
    private List<Category> categories;
}