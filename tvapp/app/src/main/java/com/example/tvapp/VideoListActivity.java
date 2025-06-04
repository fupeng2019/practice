package com.example.tvapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {
    public static final String EXTRA_SUBCATEGORY_NAME = "subcat";

    private final VideoAdapter adapter = new VideoAdapter();
    private List<Video> allVideos;
    private int page = 0;
    private static final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchView searchView = new SearchView(this);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        recyclerView.setAdapter(adapter);
        setContentView(recyclerView);
        addContentView(searchView, new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        String subName = getIntent().getStringExtra(EXTRA_SUBCATEGORY_NAME);
        SubCategory subCategory = findSubCategory(subName);
        if (subCategory == null) finish();

        allVideos = subCategory.getVideos();
        loadNextPage();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
                if (lm != null && lm.findLastVisibleItemPosition() >= adapter.getItemCount() - 1) {
                    loadNextPage();
                }
            }
        });
    }

    private SubCategory findSubCategory(String name) {
        List<Category> categories = VideoRepository.getCategories();
        for (Category c : categories) {
            for (SubCategory s : c.getSubCategories()) {
                if (s.getName().equals(name)) return s;
            }
        }
        return null;
    }

    private void loadNextPage() {
        int start = page * PAGE_SIZE;
        if (start >= allVideos.size()) return;
        int end = Math.min(start + PAGE_SIZE, allVideos.size());
        List<Video> pageData = allVideos.subList(start, end);
        if (page == 0) adapter.setVideos(pageData); else adapter.addVideos(pageData);
        page++;
    }

    private void filter(String query) {
        if (TextUtils.isEmpty(query)) {
            page = 0;
            loadNextPage();
            return;
        }
        List<Video> filtered = new ArrayList<>();
        for (Video v : allVideos) {
            if (v.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(v);
            }
        }
        adapter.setVideos(filtered);
    }
}
