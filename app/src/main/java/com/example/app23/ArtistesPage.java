package com.example.app23;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArtistesPage extends AppCompatActivity {

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_page);

        // Retrieve intent data
        Artistes artiste = (Artistes) getIntent().getSerializableExtra("artiste");

        // Retrieve data into objet
        String name = artiste.getName();
        String bio = artiste.getBio();
        String photo = artiste.getPhotoUrl();

        TextView tv_firstname = findViewById(R.id.tvName);
        TextView tv_lastName = findViewById(R.id.tvBio);
        ImageView iv_photo = findViewById(R.id.ivPhoto);

        // Set content with retrieve data about intent
        tv_firstname.setText(name);
        tv_lastName.setText(bio);

        // Get Photo from URL in artiste objet
        Glide.with(getApplicationContext())
                .load(photo)
                .into(iv_photo);

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //------------
    // OPTION MENU
    //------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
}
