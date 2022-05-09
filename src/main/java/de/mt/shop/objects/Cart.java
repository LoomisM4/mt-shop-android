package de.mt.shop.objects;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Cart {
    private static final Cart cart = new Cart();
    private List<CartArticle> articles = new ArrayList<>();

    private Cart() {}

    public static Cart shared() {
        return Cart.cart;
    }

    public void clear() {
        articles.clear();
    }

    public List<CartArticle> getCartArticles() {
        return this.articles;
    }

    public void addArticle(Article article) {
        Optional<CartArticle> alreadyInside = articles.stream()
                .filter(a -> a.article.equals(article))
                .findAny();
        CartArticle cartArticle = alreadyInside.orElseGet(() -> new CartArticle(article));
        cartArticle.more();
        if (!alreadyInside.isPresent()) {
            this.articles.add(cartArticle);
        }
    }

    public void removeArticle(Article article) {
        Optional<CartArticle> alreadyInside = articles.stream()
                .filter(a -> a.article.equals(article))
                .findAny();
        alreadyInside.ifPresent(ca -> ca.less());
    }

    public double calcTotalPrice() {
        return articles.stream().reduce(0.0,
                (sum, c) -> sum = sum + c.calcPositionPrice(),
                (d1, d2) -> d1 + d2);
    }

    public class CartArticle {
        private Article article;
        private int quantity;

        public CartArticle(Article article) {
            this.article = article;
            this.quantity = 0;
        }

        public void more() {
            this.quantity++;
        }

        public void less() {
            if (this.quantity > 0)
                this.quantity--;
        }

        public Article getArticle() {
            return this.article;
        }

        public int getQuantity() {
            return quantity;
        }

        public double calcPositionPrice() {
            return article.getPrice() * quantity;
        }
    }
}
