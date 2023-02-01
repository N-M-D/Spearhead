package com.example.spearhead;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Playlist> playlists;

    public PlaylistsAdapter(RecyclerViewInterface recyclerViewInterface, List<Playlist> playlists) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int template;
        template = R.layout.activity_playlist_card_template;
        View view = LayoutInflater.from(parent.getContext()).inflate(template, parent, false);
        final PlaylistsAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.playlistThumbnail.setImageResource(playlists.get(position).getThumbnail());
        holder.playlistName.setText(playlists.get(position).getPlaylistName());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView playlistThumbnail;
        private TextView playlistName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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
