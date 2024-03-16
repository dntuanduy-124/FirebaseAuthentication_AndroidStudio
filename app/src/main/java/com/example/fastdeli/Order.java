package com.example.fastdeli;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastdeli.model.CartM;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import com.example.fastdeli.adapter.ProductAdapter;
import java.util.List;
import com.example.fastdeli.model.Product;

public class Order extends AppCompatActivity implements ProductAdapter.OnAddButtonClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private FirebaseFirestore db;
    private CartM cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(); // Pass the activity context to the adapter
        adapter.setOnAddButtonClickListener(this); // Set the click listener for the "add" button
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadProducts();

    }

    @Override
    public void onAddButtonClick(Product product) {
        addToCart(product);
        showSuccessMessage();
    }

    private void loadProducts() {
        db.collection("product")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Product> products = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            products.add(product);
                        }
                        adapter.setProducts(products);
                    } else {
                        // Handle errors
                    }
                });
    }

    private void addToCart(Product product) {
        // Add the selected product to the cart
        // You need to implement this method based on your cart logic
        cart.addProduct(product);
    }

    private void showSuccessMessage() {
        // Show a Snackbar with a success message
        Snackbar.make(findViewById(android.R.id.content), "Product added to cart successfully", Snackbar.LENGTH_SHORT).show();
    }
}
