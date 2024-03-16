package com.example.fastdeli.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.fastdeli.R;
import com.example.fastdeli.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tvCategory);
            categoryImage = itemView.findViewById(R.id.ivCategory);
        }

        public void bind(Category category) {
            if (category.getImage() != null && (category.getImage().startsWith("http://") || category.getImage().startsWith("https://"))) {
                // Nếu là URL hợp lệ, sử dụng Glide để load hình ảnh
                Glide.with(itemView.getContext()).load(category.getImage()).into(categoryImage);
            } else {
                categoryImage.setImageResource(R.drawable.mrfresh);
            }
            categoryName.setText(category.getName());

        }
    }
}
