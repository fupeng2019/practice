package com.example.tvapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(recyclerView);

        List<Category> categories = VideoRepository.getCategories();
        CategoryAdapter adapter = new CategoryAdapter(categories, this::openSubCategories);
        recyclerView.setAdapter(adapter);
    }

    private void openSubCategories(Category category) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra(SubCategoryActivity.EXTRA_CATEGORY_NAME, category.getName());
        startActivity(intent);
    }
}
