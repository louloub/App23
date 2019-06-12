package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ArtistesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_page);

        // Retrieve intent data
        Artistes artiste = (Artistes) getIntent().getSerializableExtra("artiste");

        // Retrieve data into objet
        String firstName = artiste.getFirstname();
        String lastName = artiste.getLastname();
        String age = artiste.getAge();

        TextView tv_firstname = findViewById(R.id.textViewTitle);
        TextView tv_lastName = findViewById(R.id.textViewShortDesc);
        TextView tv_age = findViewById(R.id.age);

        // Set content with retrieve data about intent
        tv_firstname.setText(firstName);
        tv_lastName.setText(lastName);
        tv_age.setText(age);
    }
}
