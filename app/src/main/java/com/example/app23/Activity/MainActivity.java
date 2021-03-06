package com.example.app23.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.app23.R;

public class MainActivity extends OptionMenuActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BUTTON ANIMATION
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // BUTTON PODCAST
        Button btn_podcast = findViewById(R.id.carre_podcast);
        btn_podcast.setOnClickListener(v -> {
            btn_podcast.startAnimation(animation);
            openPodcastActivity();
        });

        // BUTTON ARTISTES
        Button btn_artistes = findViewById(R.id.carre_artistes);
        btn_artistes.setOnClickListener(v -> {
            btn_artistes.startAnimation(animation);
            openArtistesActivity();
        });

        // BUTTON ARTICLES
        Button btn_articles = findViewById(R.id.carre_articles);
        btn_articles.setOnClickListener(v -> {
            btn_articles.startAnimation(animation);
            openArticlesActivity();
        });

        // BUTTON ARTICLES
        Button btn_event = findViewById(R.id.carre_evenements);
        btn_event.setOnClickListener(v -> {
            btn_event.startAnimation(animation);
            openEventActivity();
        });

        // ALERTE DIALOG
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            alertDialog();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.commit();
        }
    }

    //--------
    // INTENT
    //--------
    public void openPodcastActivity() {
        Intent intent = new Intent(this, PodcastActivity.class);
        startActivity(intent);
    }

    public void openArtistesActivity() {
        Intent intent = new Intent(this, ArtistesListActivity.class);
        startActivity(intent);
    }

    public void openArticlesActivity() {
        Intent intent = new Intent(this, ArticlesListActivity.class);
        startActivity(intent);
    }

    public void openEventActivity() {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }
}
