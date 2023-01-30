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
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Music> musicList;
    private Playlist playlist;
    private boolean musicPage = true;

    public MusicListAdapter(List<Music> musicList, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int template;
        if(musicPage){
            template = R.layout.activity_music_card_template;
        }else{
            template = R.layout.activity_playlist_card_template;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(template, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.MyViewHolder holder, int position) {
        if(musicPage){
            holder.musicThumbnail.setImageResource(musicList.get(position).getImg());
            Log.d("Img: ", musicList.get(position).getImg() + "");
            holder.musicTitle.setText(musicList.get(position).getName());
        }else{
            holder.playlistThumbnail.setImageResource(playlist.getThumbnail());
            holder.playlistName.setText(playlist.getPlaylistName());
        }

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public void updateDataSet(List<Music> newDataItems) {
        musicList = newDataItems;
        notifyDataSetChanged();
        musicPage = true;
    }

    public void updateDataSet(Playlist newDataItems) {
        playlist = newDataItems;
        notifyDataSetChanged();
        musicPage = false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView musicThumbnail, playlistThumbnail;
        private TextView musicTitle, playlistName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            musicThumbnail = itemView.findViewById(R.id.MusicThumbnail);
            musicTitle = itemView.findViewById(R.id.MusicTitle);
            playlistThumbnail = itemView.findViewById(R.id.playlistThumbnail);
            playlistName = itemView.findViewById(R.id.playlistName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
