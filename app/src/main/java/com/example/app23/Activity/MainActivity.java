package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.BonsPlansAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.Object.Lieux;
import com.example.app23.Object.Preventes;
import com.example.app23.R;
import com.example.app23.controller.BonsPlansController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends OptionMenuActivity {

    private Context mCtx;
    private static final String NAME_FOR_ACTIONBAR = "YourDJ";
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String TAG = "MainActivity" ;
    ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventList = new ArrayList<>();

        // DISABLE TILE TOOLBAR
        // getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

        // LOGIN ACTIVITY
        /*Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/

        // BUTTON ANIMATION
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);

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

        // BUTTON EVENT
        Button btn_event = findViewById(R.id.carre_evenements);
        btn_event.setOnClickListener(v -> {
            btn_event.startAnimation(animation);
            openEventActivity();
        });

        // BUTTON BONS PLANS
        Button btn_bons_plans = findViewById(R.id.carre_bonsplans);
        btn_bons_plans.setOnClickListener(v -> {
            btn_bons_plans.startAnimation(animation);
            openBonsPlansActivity();
        });

        // ALERTE DIALOG
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            alertDialogCityChoice();
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

    private void openEventActivity() {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    private void openBonsPlansActivity() {
        Intent intent = new Intent(this, BonsPlansListActivity.class);
        startActivity(intent);
    }
}
