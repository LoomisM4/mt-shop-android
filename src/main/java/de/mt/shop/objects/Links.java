package de.mt.shop.objects;

import java.util.List;

import lombok.Data;

@Data
public class Links {
    private Link spotlightImage, details, preview, subcategories, articles;
    private List<Link> images;
}
