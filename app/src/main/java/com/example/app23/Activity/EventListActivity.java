package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.EventAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.Object.Lieux;
import com.example.app23.Object.Preventes;
import com.example.app23.R;
import com.example.app23.Swipe.OnSwipeTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventListActivity extends OptionMenuActivity implements View.OnTouchListener  {

    private static final String TAG = "EventListActiviy";
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event.json";

    // a list to store all the event
    List<Event> eventList;

    // recyclerview
    RecyclerView recyclerViewEventList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // getting the recyclerview from xml
        recyclerViewEventList = findViewById(R.id.recylcerViewEventList);

        recyclerViewEventList.setHasFixedSize(true);
        recyclerViewEventList.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        eventList = new ArrayList<>();

        mContext = getApplicationContext();

        // textView = findViewById(R.id.textView);

        loadEvents();

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //---------------
        // LISTENER SWIPE
        //---------------
        recyclerViewEventList.setOnTouchListener(new OnSwipeTouchListener(this) {
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
                Intent intent = new Intent(EventListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    private void loadEvents()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayEventRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, response ->
                {
                    try {
                        Log.d(TAG, " JsonArrayRequest in try jsonArrayEventRequest " +response);

                        // Browse request contain json
                        for (int i = 0; i < response.length(); i++) {

                            Log.d(TAG, " JsonArrayRequest in for " +i);

                            // Getting object from json array
                            JSONObject eventJsonObject = response.getJSONObject(i);

                            String nameEvent = eventJsonObject.getString("name");
                            String photo = eventJsonObject.getString("photo_url");

                            // DATE
                            String dateStartDate = eventJsonObject.getString("dateStart");
                            SimpleDateFormat dateStartString = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.getDefault());
                            Date dateStart = dateStartString.parse(dateStartDate);

                            String dateEndDate = eventJsonObject.getString("dateEnd");
                            SimpleDateFormat dateEndString = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.getDefault());
                            Date dateEnd = dateEndString.parse(dateEndDate);

                            String facebookUrl = eventJsonObject.getString("facebook_url");

                            //--------------------------
                            // RETRIEVE PREVENTES OBJECT
                            //--------------------------
                            int preventesNbr = 0;
                            int preventesPrix = 0;
                            Preventes preventesNew = new Preventes(preventesNbr,preventesPrix);

                            if(eventJsonObject.has("preventes"))
                            {
                                if (!eventJsonObject.isNull("preventes"))
                                {
                                    JSONObject preventes = eventJsonObject.getJSONObject("preventes");
                                    // get data JSONArray from preventes
                                    if (preventes.has("nombre"))
                                    {
                                        if (!preventes.isNull("nombre"))
                                        {
                                            int newPreventesNbr = eventJsonObject.getInt("nombre");
                                            preventesNew.setNombre(newPreventesNbr);
                                        } else {
                                        }
                                    } else {
                                        // details2 not found
                                        if (preventes.has("prix"))
                                        {
                                            if (!preventes.isNull("prix"))
                                            {
                                                int newPreventesPrix = eventJsonObject.getInt("prix");
                                                preventesNew.setPrix(newPreventesPrix);
                                            } else {
                                                // details2 found but null
                                            }
                                        } else {
                                        }
                                    }
                                } else {

                                }
                            } else {
                            }

                            //------------------------
                            // RETRIEVE ARTISTES OBJET
                            //------------------------
                            String nameArtiste = "";
                            String bioArtiste = "";
                            String photoUrlArtiste = "";
                            String facebookUrlArtiste = "";
                            String soundcloudUrlArtiste = "";
                            String beatportUrlArtiste = "";
                            String mixcloudUrlArtiste = "";
                            String twitterUrlArtiste = "";
                            String residentAdvisorUrlArtiste = "";
                            String instagramUrlArtiste = "";
                            String siteUrlArtiste = "";

                            Artistes artiste = new Artistes(nameArtiste,bioArtiste,photoUrlArtiste,facebookUrlArtiste,
                                    soundcloudUrlArtiste,beatportUrlArtiste,mixcloudUrlArtiste,twitterUrlArtiste,
                                    residentAdvisorUrlArtiste,instagramUrlArtiste,siteUrlArtiste);

                            if(eventJsonObject.has("artistes"))
                            {
                                if (!eventJsonObject.isNull("artistes"))
                                {
                                    JSONObject artistes = eventJsonObject.getJSONObject("artistes");
                                    // get data JSONArray from artistes
                                    if (artistes.has("name"))
                                    {
                                        if (!artistes.isNull("name"))
                                        {
                                            String newNameArtiste = eventJsonObject.getString("name");
                                            artiste.setName(newNameArtiste);
                                        } else {
                                        }
                                    }

                                    if (artistes.has("facebook_url"))
                                    {
                                        if (!artistes.isNull("facebook_url"))
                                        {
                                            String facebookUrlNew = eventJsonObject.getString("facebook_url");
                                            artiste.setFacebookUrl(facebookUrlNew);
                                        } else {
                                        }
                                    }

                                } else {

                                }
                            } else {

                            }

                            //------------------------
                            // RETRIEVE LIEUX OBJET
                            //------------------------

                            String photoUrlLieux = "";
                            String nameLieux = "";
                            String adresseLieux = "";
                            String mapLieuIframeLieux = "";
                            String facebookUrlLieux = "";
                            String siteUrlLieux = "";

                            Lieux lieuxEvent = new Lieux(photoUrlLieux,nameLieux,adresseLieux,mapLieuIframeLieux,
                                    facebookUrlLieux,siteUrlLieux);

                            if(eventJsonObject.has("lieux"))
                            {
                                if (!eventJsonObject.isNull("lieux"))
                                {
                                    JSONObject lieux = eventJsonObject.getJSONObject("lieux");
                                    // get data JSONArray from artistes
                                    if (lieux.has("adresse"))
                                    {
                                        if (!lieux.isNull("adresse"))
                                        {
                                            String newAdresseLieux = eventJsonObject.getString("adresse");
                                            artiste.setName(newAdresseLieux);
                                        } else {
                                        }
                                    }
                                } else {

                                }
                            } else {

                            }

                            Event event = new Event(nameEvent,photo,dateStart,dateEnd,facebookUrl,preventesNew,artiste,lieuxEvent);

                            eventList.add(event);

                            // creating adapter object and setting it to recyclerview
                            EventAdapter adapter = new EventAdapter(EventListActivity.this, eventList);
                            recyclerViewEventList.setAdapter(adapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                },
                        error -> {
                            // Do something when error occurred
                        }
                );

        requestQueue.add(jsonArrayEventRequest);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
