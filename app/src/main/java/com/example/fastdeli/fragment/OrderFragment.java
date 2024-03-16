package com.example.fastdeli.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastdeli.R;
import com.example.fastdeli.adapter.ProductAdapter;
import com.example.fastdeli.model.CartM;
import com.example.fastdeli.model.Product;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements ProductAdapter.OnAddButtonClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private FirebaseFirestore db;
    private CartM cart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProductAdapter(); // Pass the activity context to the adapter
        adapter.setOnAddButtonClickListener(this); // Set the click listener for the "add" button
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadProducts();

        return view;
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
        Snackbar.make(getView(), "Product added to cart successfully", Snackbar.LENGTH_SHORT).show();
    }
}
