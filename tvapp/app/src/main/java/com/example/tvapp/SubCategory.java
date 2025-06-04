package com.example.tvapp;

import java.util.List;

public class SubCategory {
    private final String name;
    private final List<Video> videos;

    public SubCategory(String name, List<Video> videos) {
        this.name = name;
        this.videos = videos;
    }

    public String getName() {
        return name;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
