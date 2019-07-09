package com.example.app23.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.app23.R;

public class ArtistesEventActivity extends AppCompatActivity {

    RecyclerView recyclerViewArtistesEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_model);

        recyclerViewArtistesEventList = findViewById(R.id.recyclerViewArtistesEventList);

        recyclerViewArtistesEventList.setLayoutManager(new LinearLayoutManager(this));

    }
}
