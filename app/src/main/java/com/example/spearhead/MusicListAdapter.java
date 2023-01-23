package com.example.spearhead;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {

    private List<Music> musicList;

    public MusicListAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_music_card_template, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.MyViewHolder holder, int position) {
        holder.musicThumbnail.setImageResource(musicList.get(position).getImg());
        Log.d("Img: ", musicList.get(position).getImg() + "");
        holder.musicTitle.setText(musicList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView musicThumbnail;
        private TextView musicTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            musicThumbnail = itemView.findViewById(R.id.MusicThumbnail);
            musicTitle = itemView.findViewById(R.id.MusicTitle);
        }
    }
}
