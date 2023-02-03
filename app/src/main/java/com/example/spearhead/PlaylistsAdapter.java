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
        int musicCount = playlists.get(position).getMusicList().size();
        if(musicCount == 1){
            holder.playlistThumbnail.setImageResource(playlists.get(position).getMusicList().get(0).getImg());
        }else if (musicCount == 2){
            holder.halfImg1.setVisibility(View.VISIBLE);
            holder.halfImg2.setVisibility(View.VISIBLE);
            holder.halfImg2.setImageResource(playlists.get(position).getMusicList().get(0).getImg());
            holder.halfImg2.setImageResource(playlists.get(position).getMusicList().get(1).getImg());
        }else if (musicCount == 3){
            holder.quarterImg1.setVisibility(View.VISIBLE);
            holder.quarterImg2.setVisibility(View.VISIBLE);
            holder.quarterImg3.setVisibility(View.VISIBLE);

            holder.quarterImg1.setImageResource(playlists.get(position).getMusicList().get(0).getImg());
            holder.quarterImg2.setImageResource(playlists.get(position).getMusicList().get(1).getImg());
            holder.quarterImg3.setImageResource(playlists.get(position).getMusicList().get(2).getImg());
        }else{
            holder.quarterImg1.setVisibility(View.VISIBLE);
            holder.quarterImg2.setVisibility(View.VISIBLE);
            holder.quarterImg3.setVisibility(View.VISIBLE);
            holder.quarterImg4.setVisibility(View.VISIBLE);

            Log.d("Pos", position + "");
            Log.d("Size", playlists.size() + "");

            holder.quarterImg1.setImageResource(playlists.get(position).getMusicList().get(0).getImg());
            holder.quarterImg2.setImageResource(playlists.get(position).getMusicList().get(1).getImg());
            holder.quarterImg3.setImageResource(playlists.get(position).getMusicList().get(2).getImg());
            holder.quarterImg4.setImageResource(playlists.get(position).getMusicList().get(3).getImg());
        }
        holder.playlistName.setText(playlists.get(position).getPlaylistName());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView playlistThumbnail, quarterImg1, quarterImg2, quarterImg3, quarterImg4, halfImg1, halfImg2;
        private TextView playlistName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistThumbnail = itemView.findViewById(R.id.playlistThumbnail);
            quarterImg1 = itemView.findViewById(R.id.quarterImg1);
            quarterImg2 = itemView.findViewById(R.id.quarterImg2);
            quarterImg3 = itemView.findViewById(R.id.quarterImg3);
            quarterImg4 = itemView.findViewById(R.id.quarterImg4);
            halfImg1 = itemView.findViewById(R.id.halfImg1);
            halfImg2 = itemView.findViewById(R.id.halfImg2);
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
