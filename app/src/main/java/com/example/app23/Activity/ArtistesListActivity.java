package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.ArtistesAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Swipe.OnSwipeTouchListener;
import com.example.app23.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ArtistesListActivity extends OptionMenuActivity implements View.OnTouchListener {

    private static final String NAME_FOR_ACTIONBAR = "Artistes";
    private static final String TAG = "ArtistesActiviy";

    // this is the JSON Data URL

    // private static final String URL = "http://192.168.64.2/artistes.json";
    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes.json";
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes4.json";
    // private static final String URL_API = "http://192.168.64.2/ApiYourDJ.php";
    // private static final String URL = "http://pastebin.com/raw/Em972E5s";

    //a list to store all the products
    List<Artistes> artistesList;

    //the recyclerview
    RecyclerView recyclerView;
    private Context mContext;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_list);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the ArtistesList
        artistesList = new ArrayList<>();

        mContext = getApplicationContext();

        loadArtistes();

        // Name for ActionBar
        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

        //---------------
        // LISTENER SWIPE
        //---------------
        recyclerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeDown() {
                // Toast.makeText(ArtistesListActivity.this, "Down", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                // Toast.makeText(ArtistesListActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeUp() {
                // Toast.makeText(ArtistesListActivity.this, "Up", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                // Toast.makeText(ArtistesListActivity.this, "Right", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ArtistesListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    public void loadArtistes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, response ->
        {
            try {
                // Browse request contain json
                for (int i = 0; i < response.length(); i++) {

                    // Getting object from json array
                    JSONObject artistesJsonObject = response.getJSONObject(i);

                    String name = artistesJsonObject.getString("name");
                    String bio = artistesJsonObject.getString("bio");
                    String photo = artistesJsonObject.getString("photo_url");
                    String facebook = artistesJsonObject.getString("facebook_url");
                    String soundcloud = artistesJsonObject.getString("soundcloud_url");
                    String beatport = artistesJsonObject.getString("beatport_url");
                    String mixcloud = artistesJsonObject.getString("mixcloud_url");
                    String twitter = artistesJsonObject.getString("twitter_url");
                    String residentAdvisor = artistesJsonObject.getString("residentAdvisor_url");
                    String instagram = artistesJsonObject.getString("instagram_url");
                    String site = artistesJsonObject.getString("site_url");

                    Artistes artiste = new Artistes(name,bio,photo,facebook,soundcloud,beatport,mixcloud,
                    twitter,residentAdvisor,instagram,site);

                    artistesList.add(artiste);

                    //creating adapter object and setting it to recyclerview
                    ArtistesAdapter adapter = new ArtistesAdapter(ArtistesListActivity.this, artistesList);
                    recyclerView.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
                },
                        error -> {
                            // Do something when error occurred
                        }
                );

        requestQueue.add(jsonArrayArtistesRequest);
    }

    public void displayMessage(String message)
    {
        if(textView!=null)
        {
            // Display text in the text view.
            textView.setText(message);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // ??
        return true;
    }
}