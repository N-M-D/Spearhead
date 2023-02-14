package com.example.spearhead;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerItemArrayAdapter extends RecyclerView.Adapter<HomeRecyclerItemArrayAdapter.HomeViewHolder> {

    private ArrayList<HomeRecyclerItem> mHomeCards;
    private HomeRecyclerViewItemClickListener mItemClickListener;

    public HomeRecyclerItemArrayAdapter(ArrayList<HomeRecyclerItem> homeCards, HomeRecyclerViewItemClickListener itemClickListener)
    {
        this.mHomeCards = homeCards;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
//Inflate RecyclerView row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_homerecycleritem, parent, false);

        //Create View Holder
        final HomeViewHolder myViewHolder = new HomeViewHolder(view);

        //Item Clicks
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemClickListener.onItemClicked(mHomeCards.get(myViewHolder.getLayoutPosition()));
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerItemArrayAdapter.HomeViewHolder holder, int position)
    {
        //Set Card Image
        //TODO SET IMAGE FUNCTION!!!!!
        if (holder.imageViewHomeCard != null) {
            holder.imageViewHomeCard.setImageResource(mHomeCards.get(position).getHomeCardImage());
        }
        //Set Card Title
        if (holder.textViewHomeCardTitle != null) {
            holder.textViewHomeCardTitle.setText(mHomeCards.get(position).getHomeCardTitle());
        }
        //Set Card Date
        if (holder.textViewHomeCardDate != null) {
            holder.textViewHomeCardDate.setText(mHomeCards.get(position).getHomeCardDate());
        }
    }

    @Override
    public int getItemCount()
    {
        System.out.println("getItemCount() - Size of Cards Array: " + mHomeCards.size());
        return mHomeCards.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        System.out.println("getItemId() - Position in array: " + position);
        return position;
    }

    //RecyclerView View Holder
    static class HomeViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewHomeCard;
        private TextView textViewHomeCardTitle;
        private TextView textViewHomeCardDate;

        HomeViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewHomeCard = itemView.findViewById(R.id.imgViewHomeCard);
            textViewHomeCardTitle = itemView.findViewById(R.id.txtHomeCardTitle);
            textViewHomeCardDate = itemView.findViewById(R.id.txtHomeCardDate);
        }
    }

    //TODO Not Necessary?
    //RecyclerView Click Listener
    public interface HomeRecyclerViewItemClickListener
    {
        void onItemClicked(HomeRecyclerItem homeCard);
    }
}
