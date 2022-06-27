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
import de.mt.shop.objects.Cart;
import de.mt.shop.ui.adapters.CartArrayAdapter;

@RequiresApi(api = Build.VERSION_CODES.N) // 4
public class CartFragment extends Fragment { // 1
    private CartArrayAdapter adapter; // 0
    private FragmentCartBinding binding; // 0

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { // 1
        binding = FragmentCartBinding.inflate(inflater, container, false); // 2
        View root = binding.getRoot(); // 2

        adapter = new CartArrayAdapter( // 2
                this.getContext(), R.layout.cart_article_layout, Cart.shared()); // 4
        adapter.setOnLessClicked(this::onLessClicked); // 2
        adapter.setOnMoreClicked(this::onMoreClicked); // 2

        binding.cartArticles.setAdapter(adapter); // 2
        binding.buyButton.setOnClickListener(this::onBuyClicked); // 3

        updateTotalPrice(); // 1

        return root; // 1
    }

    private void updateTotalPrice() { // 1
        String totalPrice = BigDecimal.valueOf(Cart.shared().calcTotalPrice()) // 4
                .setScale(2).toString(); // 2
        binding.totalPrice.setText(String.format("%s €", totalPrice)); // 3
    }

    private void onMoreClicked(Cart.CartArticle cartArticle) { // 1
        Cart.shared().addArticle(cartArticle.getArticle()); // 3
        updateView(); // 1
    }

    private void onLessClicked(Cart.CartArticle cartArticle) { // 1
        Cart.shared().removeArticle(cartArticle.getArticle()); // 3
        updateView(); // 1
    }

    private void updateView() { // 1
        adapter.notifyDataSetChanged(); // 1
        this.updateTotalPrice(); // 1
    }

    public void onBuyClicked(View view) { // 1
        new AlertDialog.Builder(getContext()) // 3
                .setTitle("Erfolg") // 1
                .setMessage("Die Bestellung wurde abgeschickt") // 1
                .setNeutralButton("OK", null) // 1
                .show(); // 1
        Cart.shared().clear(); // 2
        this.updateView(); // 1
    }

    @Override // 1
    public void onDestroyView() { // 1
        super.onDestroyView(); // 1
        binding = null; // 1
    }
}

// 65