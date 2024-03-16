package com.example.fastdeli.model;

import com.example.fastdeli.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartM {
    private List<Product> productList;

    public CartM() {
        productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public List<Product> getProductList() {
        return productList;
    }
}
