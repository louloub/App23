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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.ArtistesAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Swipe.OnSwipeTouchListener;
import com.example.app23.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

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

    // LAST JSON WORK
    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes4.json";

    // API
    // 1
    // private static final String URL = "https://www.yourdj.fr/api/1.0/dj/?city=montpellier&page=1&contentperpages=10";

    // 2
    // private static final String URL = "https://www.yourdj.fr/api/1.0/content/";

    // 3
    // private static final String URL = "https://www.yourdj.fr/api/";

    // 4
    // TODO : VINCENT ?
    private static final String URL = "https://www.yourdj.fr/api/api.php";

    //a list to store all the products
    List<Artistes> artistesList;

    //the recyclerview
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

    //------------------------
    // LOAD ARTISTES FROM API
    //------------------------
    public void loadArtistes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : VINCENT ?
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("6GFNK892Gb+28bfG");
        Log.d(TAG,"jsonArray = " +jsonArray);

        /*JSONArray params = new JSONArray();
        params.put(Integer.parseInt("Content-Type"), "application/json; charset=UTF-8");
        params.put(Integer.parseInt("test"), "6GFNK892Gb+28bfG");
        Log.d(TAG,"test = " +params);
        return params;*/

        /*Map<String, String> MyData = new HashMap<String, String>();
        MyData.put("Field", "Value");*/

        // TODO : VINCENT ? on appel ici le jsonArray avec la key de l'API
        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.POST, URL, jsonArray, response ->
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
                    //
                },
                        error -> {
                            // Do something when error occurred
                        }
                )
        {
            /** Passing some request headers* *//*
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "6GFNK892Gb+28bfG");
                return headers;
            }*/
        };

        // new ArtistesListActivity().getInstance().addToRequestQueue(jsonArrayArtistesRequest, "headerRequest");

        requestQueue.add(jsonArrayArtistesRequest);
    }

    // 400 : 3 / wrong parametre URL
    // 401 : erreur AUTH
    // 404 : 2 / erreur URL

    /*{
            JSONArray jsonArray = new JSONArray();
        jsonArray.put("6GFNK892Gb+28bfG");
        Log.d(TAG,"jsonArray = " +jsonArray);

            *//** Passing some request headers* *//**//*
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "6GFNK892Gb+28bfG");
                return headers;*//*

     *//*@Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("apiKey", "6GFNK892Gb");
                return params;*//*

     *//*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "6GFNK892Gb+28bfG");
                return headers;
            }*//*
        };*/

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    /*public void loadArtistes()
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
    }*/

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