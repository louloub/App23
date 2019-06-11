package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ArtistesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_page);

        Bundle bundle = getIntent().getExtras();

    }
}
