package com.example.fastdeli.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastdeli.R;
import com.example.fastdeli.model.Product;
import com.example.fastdeli.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private List<Product> cartProducts = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvItemCount;
    private TextView tvTotalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewOrder);
        tvItemCount = view.findViewById(R.id.tvItemCount);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        // Get product from arguments (you may need to pass product list through arguments)
        Bundle args = getArguments();
        if (args != null) {
            Product product = args.getParcelable("product");
            if (product != null) {
                cartProducts.add(product);
                // Update RecyclerView
                adapter.setProducts(cartProducts);
                // Update item count
                updateItemCount();
                // Update total price
                updateTotalPrice();
            }
        }

        return view;
    }

    private void updateItemCount() {
        int itemCount = cartProducts.size();
        tvItemCount.setText("Item count: " + itemCount);
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        tvTotalPrice.setText(String.format(Locale.getDefault(), "Total price: $%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : cartProducts) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        return totalPrice;
    }
}
