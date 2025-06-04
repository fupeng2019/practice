package com.example.tvapp;

import java.util.ArrayList;
import java.util.List;

public class VideoRepository {
    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        // Sample data; in real app data might come from network or database
        List<Video> actionVideos = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            actionVideos.add(new Video("Action Video " + i, "https://example.com/action" + i + ".mp4"));
        }
        SubCategory actionSub = new SubCategory("Action", actionVideos);

        List<Video> dramaVideos = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            dramaVideos.add(new Video("Drama Video " + i, "https://example.com/drama" + i + ".mp4"));
        }
        SubCategory dramaSub = new SubCategory("Drama", dramaVideos);

        List<SubCategory> movieSubs = new ArrayList<>();
        movieSubs.add(actionSub);
        movieSubs.add(dramaSub);

        categories.add(new Category("Movies", movieSubs));

        return categories;
    }
}
