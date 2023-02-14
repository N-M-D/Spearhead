package com.example.spearhead;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerViewActivity extends AppCompatActivity {
        private RecyclerView hRecyclerView;
        private ArrayList<HomeRecyclerItem> mHomeCards = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_home);
            bindHomeCardsData();
            setUIRef();
        }
        private void setUIRef()
        {
            //Reference of RecyclerView
            hRecyclerView = findViewById(R.id.recyclerviewHome);
            //Linear Layout Manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeRecyclerViewActivity.this, RecyclerView.VERTICAL, false);
            //Set Layout Manager to RecyclerView
            hRecyclerView.setLayoutManager(linearLayoutManager);

            //Create adapter
            HomeRecyclerItemArrayAdapter hRecyclerItemArrayAdapter = new HomeRecyclerItemArrayAdapter(mHomeCards, new HomeRecyclerItemArrayAdapter.HomeRecyclerViewItemClickListener()
            {
                //Handling clicks
                @Override
                public void onItemClicked(HomeRecyclerItem homeCard)
                {
                    //TODO Remove when toast logging is no longer needed
                    Toast.makeText(HomeRecyclerViewActivity.this, homeCard.getHomeCardTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            //Set adapter to RecyclerView
            hRecyclerView.setAdapter(hRecyclerItemArrayAdapter);
        }
        //TODO Task Data is here
        private void bindHomeCardsData()
        {
            mHomeCards.add(new HomeRecyclerItem(R.drawable.mahjong,  "Learn to Play Mahjong","01/04/23"));
            mHomeCards.add(new HomeRecyclerItem(R.drawable.team_lunch,"Lunch with the Team", "05/02/23"));
            mHomeCards.add(new HomeRecyclerItem(R.drawable.coding, "Do Coding Assignment", "13/02/23"));

        }
}
