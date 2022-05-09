package de.mt.shop.ui.cart;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

import de.mt.shop.R;
import de.mt.shop.databinding.FragmentCartBinding;
import de.mt.shop.objects.Article;
import de.mt.shop.objects.Cart;
import de.mt.shop.ui.adapters.CartArrayAdapter;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CartFragment extends Fragment {
    private CartArrayAdapter adapter;
    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new CartArrayAdapter(
                this.getContext(), R.layout.cart_article_layout, Cart.shared());
        adapter.setOnLessClicked(this::onLessClicked);
        adapter.setOnMoreClicked(this::onMoreClicked);

        binding.cartArticles.setAdapter(adapter);
        binding.buyButton.setOnClickListener(this::onBuyClicked);

        updateTotalPrice();

        return root;
    }

    private void updateTotalPrice() {
        String totalPrice = BigDecimal.valueOf(Cart.shared().calcTotalPrice())
                .setScale(2).toString();
        binding.totalPrice.setText(String.format("%s €", totalPrice));
    }

    private void onMoreClicked(Cart.CartArticle cartArticle) {
        Cart.shared().addArticle(cartArticle.getArticle());
        updateView();
    }

    private void onLessClicked(Cart.CartArticle cartArticle) {
        Cart.shared().removeArticle(cartArticle.getArticle());
        updateView();
    }

    private void updateView() {
        adapter.notifyDataSetChanged();
        this.updateTotalPrice();
    }

    public void onBuyClicked(View view) {
        new AlertDialog.Builder(getContext())
                .setTitle("Erfolg")
                .setMessage("Die Bestellung wurde abgeschickt")
                .setNeutralButton("OK", null)
                .show();
        Cart.shared().clear();
        this.updateView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}