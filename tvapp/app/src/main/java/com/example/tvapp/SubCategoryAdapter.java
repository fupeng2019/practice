package com.example.tvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(SubCategory subCategory);
    }

    private final List<SubCategory> subCategories;
    private final OnItemClickListener listener;

    public SubCategoryAdapter(List<SubCategory> subCategories, OnItemClickListener listener) {
        this.subCategories = subCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
        holder.textView.setText(subCategory.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(subCategory));
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
