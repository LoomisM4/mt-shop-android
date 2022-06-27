package de.mt.shop.objects;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.mt.shop.objects.gen.Article;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class Cart { // 1
    private static final Cart cart = new Cart(); // 2
    private List<CartArticle> articles = new ArrayList<>(); // 2

    private Cart() {} // 1

    public static Cart shared() { // 1
        return Cart.cart; // 2
    }

    public void clear() { // 1
        articles.clear(); // 1
    }

    public List<CartArticle> getCartArticles() { // 1
        return this.articles; // 2
    }

    public void addArticle(Article article) { // 1
        Optional<CartArticle> alreadyInside = articles.stream() // 2
                .filter(a -> a.article.equals(article)) // 3
                .findAny(); // 1
        CartArticle cartArticle = alreadyInside.orElseGet(() -> new CartArticle(article)); // 3
        cartArticle.more(); // 1
        if (!alreadyInside.isPresent()) { // 3
            this.articles.add(cartArticle); // 2
        }
    }

    public void removeArticle(Article article) { // 1
        Optional<CartArticle> alreadyInside = articles.stream() // 2
                .filter(a -> a.article.equals(article)) // 3
                .findAny(); // 1
        alreadyInside.ifPresent(ca -> ca.less()); // 2
    }

    public double calcTotalPrice() { // 1
        return articles.stream().reduce(0.0, // 3
                (sum, c) -> sum = sum + c.calcPositionPrice(), // 3
                (d1, d2) -> d1 + d2); // 1
    }

    public class CartArticle { // 1
        private Article article; // 0
        private int quantity; // 0

        public CartArticle(Article article) { // 1
            this.article = article; // 2
            this.quantity = 0; // 2
        }

        public void more() { // 1
            this.quantity++; // 2
        }

        public void less() { // 1
            if (this.quantity > 0) // 3
                this.quantity--; // 2
        }

        public Article getArticle() { // 1
            return this.article; // 2
        }

        public int getQuantity() { // 1
            return quantity; // 1
        }

        public double calcPositionPrice() { // 1
            return article.getPrice() * quantity; // 3
        }
    }
}

// 75