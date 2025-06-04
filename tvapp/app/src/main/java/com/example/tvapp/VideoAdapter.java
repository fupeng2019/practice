package com.example.tvapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final List<Video> videos = new ArrayList<>();

    public void setVideos(List<Video> data) {
        videos.clear();
        videos.addAll(data);
        notifyDataSetChanged();
    }

    public void addVideos(List<Video> data) {
        int start = videos.size();
        videos.addAll(data);
        notifyItemRangeInserted(start, data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.textView.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
