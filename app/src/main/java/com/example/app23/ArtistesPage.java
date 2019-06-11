package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ArtistesPage extends AppCompatActivity {

    private static final String LOG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_page);

        Artistes artiste = (Artistes) getIntent().getSerializableExtra("artiste");

        String firstName = artiste.getFirstname();
        String lastName = artiste.getLastname();
        String age = artiste.getAge();

    }
}
