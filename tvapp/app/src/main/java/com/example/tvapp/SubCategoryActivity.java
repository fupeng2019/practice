package com.example.tvapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_NAME = "category";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(recyclerView);

        String categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        Category category = findCategory(categoryName);
        if (category == null) finish();

        SubCategoryAdapter adapter = new SubCategoryAdapter(category.getSubCategories(), this::openVideos);
        recyclerView.setAdapter(adapter);
    }

    private Category findCategory(String name) {
        List<Category> categories = VideoRepository.getCategories();
        for (Category c : categories) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    private void openVideos(SubCategory subCategory) {
        Intent intent = new Intent(this, VideoListActivity.class);
        intent.putExtra(VideoListActivity.EXTRA_SUBCATEGORY_NAME, subCategory.getName());
        startActivity(intent);
    }
}
