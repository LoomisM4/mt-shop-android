package de.mt.shop.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import de.mt.shop.R;
import de.mt.shop.objects.Article;
import de.mt.shop.objects.Cart;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CartArrayAdapter extends ArrayAdapter {
    Cart cart;
    Consumer<Cart.CartArticle> onLessClicked;
    Consumer<Cart.CartArticle> onMoreClicked;

    public CartArrayAdapter(@NonNull Context context, int resource, Cart cart) {
        super(context, resource, cart.getCartArticles());
        this.cart = cart;
    }

    public void setOnLessClicked(Consumer<Cart.CartArticle> onLessClicked) {
        this.onLessClicked = onLessClicked;
    }

    public void setOnMoreClicked(Consumer<Cart.CartArticle> onMoreClicked) {
        this.onMoreClicked = onMoreClicked;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cart_article_layout, null);
        }

        Cart.CartArticle cartArticle = cart.getCartArticles().get(position);

        TextView name = v.findViewById(R.id.nameText);
        name.setText(cartArticle.getArticle().getName());
        TextView amount = v.findViewById(R.id.quantityText);
        amount.setText(String.valueOf(cartArticle.getQuantity()));
        TextView positionPrice = v.findViewById(R.id.positionPriceText);
        String positionPriceValue = BigDecimal.valueOf(cartArticle.calcPositionPrice())
                .setScale(2).toString();
        positionPrice.setText(String.format("%s €", positionPriceValue));

        if (this.onLessClicked != null) {
            Button lessButton = v.findViewById(R.id.lessButton);
            lessButton.setOnClickListener(l -> this.onLessClicked.accept(cartArticle));
        }

        if (this.onMoreClicked != null) {
            Button moreButton = v.findViewById(R.id.moreButton);
            moreButton.setOnClickListener(l -> this.onMoreClicked.accept(cartArticle));
        }

        return v;
    }
}
