package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.ArtistesAdapter;
import com.example.app23.Builder.GetParamsBuilder;
import com.example.app23.Builder.JsonArtistesObjectsBuilder;
import com.example.app23.Builder.UrlBuilder;
import com.example.app23.Object.Artistes;
import com.example.app23.Swipe.OnSwipeTouchListener;
import com.example.app23.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistesListActivity extends OptionMenuActivity implements View.OnTouchListener {

    private static final String NAME_FOR_ACTIONBAR = "Artistes";
    private static final String TAG = "ArtistesActiviy";
    // String choiceVille = getIntent().getStringExtra("ChoiceVille");

    // LAST JSON WORK
    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes4.json";

    // API
    private static final String URL = "https://www.yourdj.fr/api/1.0/dj/";

    // Class Variable for URL
    private int page = 1;
    final int contentperpages = 10;

    // List to store all artistes
    List<Artistes> artistesList;

    // recyclerview
    RecyclerView recyclerView;
    private Context mContext;

    TextView textView;

    // API
    private RequestQueue requestQueue;
    private static ArtistesListActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // API
        mInstance = this;

        setContentView(R.layout.activity_artistes_list);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the ArtistesList
        artistesList = new ArrayList<>();

        mContext = getApplicationContext();

        // Name for ActionBar
        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

        // Show content form city choice
        SharedPreferences settings = getSharedPreferences(CITY_CHOICE, Context.MODE_PRIVATE);
        String city = settings.getString("MyCityChoice", "");
        loadArtistes(city, contentperpages);

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

    // API
    public static synchronized ArtistesListActivity getInstance() {
        return mInstance;
    }

    // API
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    // API
    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    // API
    public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    //-------------------------------------
    // LOAD ARTISTES FROM API (fonctionnel)
    //-------------------------------------
    public void loadArtistes(String city, int contentperpages)
    {
        // URI BUILDER
        String uri = UrlBuilder.getUrl(city, page, contentperpages);

        // GET PARAMS BUILDER
        JSONObject getParams = GetParamsBuilder.getParams(city,page,contentperpages);

        recyclerView.addOnScrollListener(new RecyclerViewScrollListener((LinearLayoutManager)
                this.recyclerView.getLayoutManager(), new RecyclerViewScrollListener.OnScrollListener()
        {
            @Override
            public void onScrollingUp() {
                Log.d(TAG,"onScrollingUp");
            }

            public void onBottomReached() {
                Log.d(TAG,"onBottomReached");

                page++;
                loadArtistes(city, contentperpages);
                Log.d(TAG, "onBottomReached page = " +page);
            }
        }));

        //--------------------
        // JSON OBJECT REQUEST
        //--------------------

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                uri,getParams, response ->
        {
            try {
                JSONArray jsonArrayArtistesAPI = (JSONArray) response.get("results");

                for (int i = 0; i < jsonArrayArtistesAPI.length(); i++)
                {
                    artistesList.add(JsonArtistesObjectsBuilder.getArtistesObjectsFromAPI(jsonArrayArtistesAPI,i));
                }

                } catch (JSONException e) {
                e.printStackTrace();
                }

                if (recyclerView.getAdapter() == null) {
                    ArtistesAdapter adapter = new ArtistesAdapter(ArtistesListActivity.this, artistesList);
                    recyclerView.setAdapter(adapter);
                }

                // le ELSE ajoute les nouveaux items à le suite des derniers items
                else {
                    recyclerView.getAdapter().notifyItemInserted(artistesList.size());
                }
        },
                error -> {
                    Log.d(TAG, "loadArtistesM error = " +error );
                })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("apikey", "6GFNK892Gb+28bfG");
                Log.d(TAG,"loadArtistesM headers = " +headers );
                return headers;
            }
        };

        // Adding the request to the queue along with a unique string tag
        ArtistesListActivity.getInstance().addToRequestQueue(jsonObjectRequest, "postRequest");
    }

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    public void loadArtistesFromJson()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.POST, URL, null, response ->
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