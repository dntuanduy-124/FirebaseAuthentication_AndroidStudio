package com.example.fastdeli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastdeli.model.Product;
import com.example.fastdeli.R;
import java.util.List;
import com.example.fastdeli.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartProducts;
    private Context context;

    public CartAdapter(List<Product> cartProducts, Context context) {
        this.cartProducts = cartProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProducts.get(position);
        holder.bind(product);

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                quantity++;
                product.setQuantity(quantity);
                holder.tvQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                if (quantity > 0) {
                    quantity--;
                    product.setQuantity(quantity);
                    holder.tvQuantity.setText(String.valueOf(quantity));
                    updateTotalPrice();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (Product product : cartProducts) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        ((Cart) context).updateTotalPrice();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuantity;
        private Button btnPlus;
        private Button btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnPlus = itemView.findViewById(R.id.btnplus);
            btnMinus = itemView.findViewById(R.id.btnminus);
        }

        public void bind(Product product) {
            tvQuantity.setText(String.valueOf(product.getQuantity()));
        }
    }
}
