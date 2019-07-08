package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
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
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event2.json";

    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event.json";
    private static final String NAME_FOR_ACTIONBAR = "Événements";

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

        loadEvents();

        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

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
                        // Browse request contain json
                        for (int i = 0; i < response.length(); i++) {

                            // Getting object from json array
                            JSONObject eventJsonObject = response.getJSONObject(i);

                            String nameEvent = eventJsonObject.getString("name_event");
                            String photoUrlEvent = eventJsonObject.getString("photo_url_event");

                            // DATE
                            String dateStartDate = eventJsonObject.getString("dateStart_event");
                            SimpleDateFormat dateStartString = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.getDefault());
                            Date dateStartEvent = dateStartString.parse(dateStartDate);

                            String dateEndDate = eventJsonObject.getString("dateEnd_event");
                            SimpleDateFormat dateEndString = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm", Locale.getDefault());
                            Date dateEndEvent = dateEndString.parse(dateEndDate);

                            // FACEBOOK
                            String facebookUrlEvent = eventJsonObject.getString("facebook_url_event");

                            // CONCOURS
                            String concoursUrl = eventJsonObject.getString("concours_url");

                            //--------------------------
                            // RETRIEVE PREVENTES OBJECT
                            //--------------------------
                            int preventesNbr = 0;
                            int preventesPrix = 0;
                            String preventesUrl = "";
                            Preventes preventesEvent = new Preventes(preventesNbr,preventesPrix, preventesUrl);

                            if(eventJsonObject.has("preventes_event"))
                            {
                                if (!eventJsonObject.isNull("preventes_event"))
                                {
                                    JSONObject preventes = eventJsonObject.getJSONObject("preventes_event");
                                    if (preventes.has("nombre_preventes"))
                                    {
                                        if (!preventes.isNull("nombre_preventes"))
                                        {
                                            int newPreventesNbr = preventes.getInt("nombre_preventes");
                                            preventesEvent.setNombre(newPreventesNbr);
                                        } else {
                                        }

                                        if (preventes.has("prix_preventes"))
                                        {
                                            if (!preventes.isNull("prix_preventes"))
                                            {
                                                int newPreventesPrix = preventes.getInt("prix_preventes");
                                                preventesEvent.setPrix(newPreventesPrix);
                                            } else {
                                            }

                                            if (preventes.has("preventes_url"))
                                            {
                                                if (!preventes.isNull("preventes_url"))
                                                {
                                                    String newPreventesUrl = preventes.getString("preventes_url");
                                                    preventesEvent.setPreventesUrl(newPreventesUrl);
                                                } else {
                                                }
                                            } else {
                                            }
                                        } else {
                                        }
                                    } else {
                                    }
                                } else {
                                }
                            } else {
                            }

                            //-------------------------
                            // RETRIEVE ARTISTES OBJECT
                            //-------------------------

                            ArrayList<Artistes> artistesList = new ArrayList<>();

                            if(eventJsonObject.has("artistes_event"))
                            {
                                if (!eventJsonObject.isNull("artistes_event"))
                                {
                                    // JSON ARRAY ARTISTES contient les 4 Artistes
                                    JSONArray jsonArrayArtistes = eventJsonObject.getJSONArray("artistes_event");

                                    if(jsonArrayArtistes.length()>0)
                                    {
                                        for (int iA = 0; iA < jsonArrayArtistes.length(); iA++)
                                        {
                                            // JSON ARTISTE FROM list : artiste au format JSON OBJECT
                                            JSONObject jsonArtisteFromList = jsonArrayArtistes.getJSONObject(iA);

                                            /*Artistes artistesFromJsonObject = new Artistes(jsonArtisteFromList);
                                            Log.d(TAG, "artistesFromJsonObject = " +artistesFromJsonObject);*/

                                            String name = "";
                                            String facebook_url = "";

                                            Artistes artistesFromJsonObject = new Artistes(name,facebook_url);

                                                if(jsonArtisteFromList.has("name_artiste"))
                                                {
                                                    String nameArtiste = jsonArtisteFromList.getString("name_artiste");
                                                    artistesFromJsonObject.setName(nameArtiste);
                                                }else {
                                                }
                                                if(jsonArtisteFromList.has("facebook_url_artiste"))
                                                {
                                                    String artisteFacebookUrl = jsonArtisteFromList.getString("facebook_url_artiste");
                                                    artistesFromJsonObject.setFacebookUrl(artisteFacebookUrl);
                                                }else {
                                                }
                                            Log.d(TAG, "jsonArtisteFromList = " +jsonArtisteFromList );
                                            artistesList.add(artistesFromJsonObject);
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

                            if(eventJsonObject.has("lieux_event"))
                            {
                                if (!eventJsonObject.isNull("lieux_event"))
                                {
                                    JSONObject lieux = eventJsonObject.getJSONObject("lieux_event");
                                    if (lieux.has("name_lieux"))
                                    {
                                        if (!lieux.isNull("name_lieux"))
                                        {
                                            String newNameLieux = lieux.getString("name_lieux");
                                            lieuxEvent.setNameLieux(newNameLieux);
                                        } else {
                                        }
                                    }
                                    if (lieux.has("adresse_lieux"))
                                    {
                                        if (!lieux.isNull("adresse_lieux"))
                                        {
                                            String newAdresseLieux = lieux.getString("adresse_lieux");
                                            lieuxEvent.setAdresse(newAdresseLieux);
                                        } else {
                                        }
                                    }
                                } else {
                                }
                            } else {
                            }

                            //---------------------------------------
                            // CREATE EVENT WITH RETRIEVE INFORMATION
                            //---------------------------------------
                            Event event = new Event(photoUrlEvent,nameEvent,dateStartEvent,dateEndEvent,
                                    facebookUrlEvent,preventesEvent,artistesList,lieuxEvent, concoursUrl);

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
