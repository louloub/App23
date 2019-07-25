package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.ArtistesAdapter;
import com.example.app23.Builder.GetParamsBuilder;
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

    final int page = 1;
    final int contentperpages = 10;

    //a list to store all the products
    List<Artistes> artistesList;

    //the recyclerview
    RecyclerView recyclerView;
    private Context mContext;

    TextView textView;

    // API
    private RequestQueue requestQueue;
    private static ArtistesListActivity mInstance;

    boolean isLoading = false;

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
        String city = settings.getString("cityChoice", "");
        loadArtistes(city,page,contentperpages);

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
    public void loadArtistesY ()
        {
            String city = "montpellier";
            String page = "1";
            String contentperpages = "10";
            String uri4 = "https://www.yourdj.fr/api/1.0/dj/?city=" +city+ "&page=" +page+ "&contentperpages=" +contentperpages+ "";
            Log.d(TAG,"loadArtistesM uri " +uri4 );

        JSONObject getparams = new JSONObject();
            try {
                getparams.put("city", "montpellier");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                getparams.put("page", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                getparams.put("content_per_pages", "10");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    uri4,getparams, response -> {
                Log.d(TAG, "loadArtistesM response = "+response );

            },
                    error -> {
                        Log.d(TAG, "loadArtistesM error = "+error );
                    }
            )
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

    //-------------------------------------
    // LOAD ARTISTES FROM API (fonctionnel)
    //-------------------------------------
    public void loadArtistes(String city, int page, int contentperpages)
    {
        // URI BUILDER
        String uri = UrlBuilder.getUrl(city, page, contentperpages);

        // GET PARAMS BUILDER
        JSONObject getParams = GetParamsBuilder.getParams(city,page,contentperpages);

        // int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

        // int lastVisibleItem, totalItemCount;

        // Method for track the end of scrolling (bottom)

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/

        // TODO : a de placer dans une autre methofe
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener((LinearLayoutManager)
                this.recyclerView.getLayoutManager(), new RecyclerViewScrollListener.OnScrollListener()
        {
            @Override
            public void onScrollingUp() {


            }

            @Override
            public void onBottomReached() {

                // TODO : appeler loadArtites en incrémentant page

            }
        }));

        //--------------------
        // JSON OBJECT REQUEST
        //--------------------

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                uri,getParams, response ->
        {
            try {

                // TODO: Move to static class builder that return the builded object
                JSONArray jsonArrayArtistesAPI = (JSONArray) response.get("results");

                for (int i = 0; i < jsonArrayArtistesAPI.length(); i++)
                {
                    JSONObject jsonArtistesObjects = jsonArrayArtistesAPI.getJSONObject(i);

                    String name = "";
                    String bio = "";
                    String photoUrl = "";
                    String facebook_url = "";
                    String soundcloudUrl = "";
                    String beatportUrl = "";
                    String mixcloudUrl = "";
                    String twitterUrl = "";
                    String residentAdvisorUrl = "";
                    String instagramUrl = "";
                    String siteUrl = "";

                    Artistes artistesFromApi = new Artistes(name,bio,photoUrl,facebook_url,
                            soundcloudUrl,beatportUrl,mixcloudUrl,twitterUrl,residentAdvisorUrl,
                            instagramUrl,siteUrl);

                    // NAME
                    if (jsonArtistesObjects.has("name"))
                    {
                        if (!jsonArtistesObjects.isNull("name"))
                        {
                            String newName = jsonArtistesObjects.getString("name");
                            artistesFromApi.setName(newName);
                        } else {
                        }
                    } else {
                    }

                    // BIO
                    if (jsonArtistesObjects.has("content"))
                    {
                        if (!jsonArtistesObjects.isNull("content"))
                        {
                            String newBio = jsonArtistesObjects.getString("content");
                            /*byte[] decodedBio = Base64.getDecoder().decode(newBio);
                            String encodedBio = Base64.getEncoder().encodeToString(decodedBio);*/
                            artistesFromApi.setBio(newBio);
                        } else {
                        }
                    } else {
                    }

                    // PHOTO
                    if (jsonArtistesObjects.has("image"))
                    {
                        if (!jsonArtistesObjects.isNull("image"))
                        {
                            String newPhotoUrl = jsonArtistesObjects.getString("image");
                            artistesFromApi.setPhotoUrl("https://www.yourdj.fr/" +newPhotoUrl);
                        } else {
                        }
                    } else {
                    }

                    // FB
                    if (jsonArtistesObjects.has("urlfb"))
                    {
                        if (!jsonArtistesObjects.isNull("urlfb"))
                        {
                            String newFacebook = jsonArtistesObjects.getString("urlfb");
                            artistesFromApi.setFacebookUrl(newFacebook);
                        } else {
                        }
                    } else {
                    }

                    // SC
                    if (jsonArtistesObjects.has("urlsc"))
                    {
                        if (!jsonArtistesObjects.isNull("urlsc"))
                        {
                            String newSoundcloud = jsonArtistesObjects.getString("urlsc");
                            artistesFromApi.setSoundcloudUrl(newSoundcloud);
                        } else {
                        }
                    } else {
                    }

                    // BP
                    if (jsonArtistesObjects.has("urlbp"))
                    {
                        if (!jsonArtistesObjects.isNull("urlbp"))
                        {
                            String newBeatport = jsonArtistesObjects.getString("urlbp");
                            artistesFromApi.setBeatportUrl(newBeatport);
                        } else {
                        }
                    } else {
                    }

                    // MC
                    if (jsonArtistesObjects.has("urlmc"))
                    {
                        if (!jsonArtistesObjects.isNull("urlmc"))
                        {
                            String newMixcloud = jsonArtistesObjects.getString("urlmc");
                            artistesFromApi.setMixcloudUrl(newMixcloud);
                        } else {
                        }
                    } else {
                    }

                    // TW
                    if (jsonArtistesObjects.has("urltw"))
                    {
                        if (!jsonArtistesObjects.isNull("urltw"))
                        {
                            String newTwitter = jsonArtistesObjects.getString("urltw");
                            artistesFromApi.setTwitterUrl(newTwitter);
                        } else {
                        }
                    } else {
                    }

                    // RA
                    if (jsonArtistesObjects.has("urlra"))
                    {
                        if (!jsonArtistesObjects.isNull("urlra"))
                        {
                            String newResidentAdvisor = jsonArtistesObjects.getString("urlra");
                            artistesFromApi.setResidentAdvisorUrl(newResidentAdvisor);
                        } else {
                        }
                    } else {
                    }

                    // INSTA
                    if (jsonArtistesObjects.has("urlinsta"))
                    {
                        if (!jsonArtistesObjects.isNull("urlinsta"))
                        {
                            String newInstagram = jsonArtistesObjects.getString("urlinsta");
                            artistesFromApi.setInstagramUrl(newInstagram);
                        } else {
                        }
                    } else {
                    }

                    // SITE
                    if (jsonArtistesObjects.has("urlsite"))
                    {
                        if (!jsonArtistesObjects.isNull("urlsite"))
                        {
                            String newSite = jsonArtistesObjects.getString("urlsite");
                            artistesFromApi.setSiteUrl(newSite);
                        } else {
                        }
                    } else {
                    }

                    artistesList.add(artistesFromApi);

                    //creating adapter object and setting it to recyclerview
                    if (recyclerView.getAdapter() == null) {
                        ArtistesAdapter adapter = new ArtistesAdapter(ArtistesListActivity.this, artistesList);
                        recyclerView.setAdapter(adapter);
                    }
                    // le ELSE ajoute les nouveaux items à le suite des derniers items
                    else {
                        recyclerView.getAdapter().notifyItemInserted(artistesList.size());
                    }
                }

                } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    Log.d(TAG, "loadArtistesM error = "+error );
                }
        )
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
    // LOAD ARTISTES FROM API
    //------------------------
    public void loadArtistesZ() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : VINCENT ?
        JSONObject jsonO = new JSONObject();
        String key = "6GFNK892Gb+28bfG";
        try {
            jsonO.put("apiKey","6GFNK892Gb+28bfG");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"jsonO = " +jsonO);

        /*JSONArray params = new JSONArray();
        params.put(Integer.parseInt("Content-Type"), "application/json; charset=UTF-8");
        params.put(Integer.parseInt("test"), "6GFNK892Gb+28bfG");
        Log.d(TAG,"test = " +params);
        return params;*/

        /*Map<String, String> MyData = new HashMap<String, String>();
        MyData.put("Field", "Value");*/

        // TODO : VINCENT ? on appel ici le jsonArray avec la key de l'API
        JsonObjectRequest jsonArrayArtistesRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, response ->
                {
                    /*// Browse request contain json
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
                    }*/

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