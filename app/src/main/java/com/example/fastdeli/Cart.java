package com.example.fastdeli;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastdeli.model.Product;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import com.example.fastdeli.adapter.ProductAdapter;

public class Cart extends AppCompatActivity {

    private List<Product> cartProducts = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvItemCount;
    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewOrder);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Assuming you have a ProductAdapter for the RecyclerView
        ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        // Get product from intent
        Product product = getIntent().getParcelableExtra("product");
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

    private void updateItemCount() {
        int itemCount = cartProducts.size();
        tvItemCount.setText("Item count: " + itemCount);
    }

    public void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        // Update the total price TextView with the calculated total price
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
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
