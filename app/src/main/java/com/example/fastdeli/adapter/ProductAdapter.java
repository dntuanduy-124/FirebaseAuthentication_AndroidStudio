package com.example.fastdeli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastdeli.fragment.OrderFragment;
import com.example.fastdeli.model.Product;
import com.example.fastdeli.R;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnItemClickListener listener;
    private Product selectedProduct;
    private OnAddButtonClickListener addButtonClickListener;

    public static Context context;

    public interface OnAddButtonClickListener {
        void onAddButtonClick(Product product);
    }
    public void setOnAddButtonClickListener(OnAddButtonClickListener listener) {
        this.addButtonClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu trữ sản phẩm được chọn khi người dùng click vào nút "Add"
                selectedProduct = product;
                // Thực hiện các hành động khác cần thiết (nếu cần)
            }
        });
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView productDetail;
        private ImageView IvProduct;
        private Button btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productDetail = itemView.findViewById(R.id.productDetail);
            IvProduct = itemView.findViewById(R.id.ivProduct);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }

        public void bind(Product product) {
            if (product.getImage() != null && (product.getImage().startsWith("http://") || product.getImage().startsWith("https://"))) {
                // Nếu là URL hợp lệ, sử dụng Glide để load hình ảnh
                Glide.with(itemView.getContext()).load(product.getImage()).into(IvProduct);
            } else {
                IvProduct.setImageResource(R.drawable.mrfresh);
            }

            Glide.with(itemView.getContext()).load(product.getImage()).into(IvProduct);
            productNameTextView.setText(product.getName());
            productDetail.setText(product.getDetail());
            productPriceTextView.setText(String.valueOf(product.getPrice()));
            // Load image for the product if needed
        }
    }
}
