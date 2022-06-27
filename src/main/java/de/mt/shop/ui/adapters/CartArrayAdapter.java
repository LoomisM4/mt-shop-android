package de.mt.shop.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.function.Consumer;

import de.mt.shop.R;
import de.mt.shop.objects.Cart;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class CartArrayAdapter extends ArrayAdapter { // 1
    Cart cart; // 0
    Consumer<Cart.CartArticle> onLessClicked; // 0
    Consumer<Cart.CartArticle> onMoreClicked; // 0

    public CartArrayAdapter(@NonNull Context context, int resource, Cart cart) { // 1
        super(context, resource, cart.getCartArticles()); // 2
        this.cart = cart; // 2
    }

    public void setOnLessClicked(Consumer<Cart.CartArticle> onLessClicked) { // 1
        this.onLessClicked = onLessClicked; // 2
    }

    public void setOnMoreClicked(Consumer<Cart.CartArticle> onMoreClicked) { // 1
        this.onMoreClicked = onMoreClicked; // 2
    }

    @NonNull // 1
    @Override // 1
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { // 1
        View v = convertView; // 1
        if (v == null) { // 2
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 5
            v = vi.inflate(R.layout.cart_article_layout, null); // 4
        }

        Cart.CartArticle cartArticle = cart.getCartArticles().get(position); // 4

        TextView name = v.findViewById(R.id.nameText); // 4
        name.setText(cartArticle.getArticle().getName()); // 3
        TextView amount = v.findViewById(R.id.quantityText); // 4
        amount.setText(String.valueOf(cartArticle.getQuantity())); // 3
        TextView positionPrice = v.findViewById(R.id.positionPriceText); // 4
        String positionPriceValue = BigDecimal.valueOf(cartArticle.calcPositionPrice()) // 3
                .setScale(2).toString(); // 2
        positionPrice.setText(String.format("%s €", positionPriceValue)); // 2

        if (this.onLessClicked != null) { // 3
            Button lessButton = v.findViewById(R.id.lessButton); // 4
            lessButton.setOnClickListener(l -> this.onLessClicked.accept(cartArticle)); // 3
        }

        if (this.onMoreClicked != null) { // 3
            Button moreButton = v.findViewById(R.id.moreButton); // 4
            moreButton.setOnClickListener(l -> this.onMoreClicked.accept(cartArticle)); // 3
        }

        return v; // 1
    }
}

// 81