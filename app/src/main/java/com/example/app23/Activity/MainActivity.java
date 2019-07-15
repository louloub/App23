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
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event2.json";
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

            /*loadEvents();
            // TODO le premier tour loadEvent renvoi une liste vide alors qu'il y a un
            // TODO un event bon plans dans la liste
            // TODO a partir du second tour de la boucle il récupérer les 3 events bons plans
            // TODO trouver une solution pour le premier tour qui est vide
            if (eventList.size()==0)
            {
                Log.d(TAG, "eventList main = 0" +eventList );
            }else {
                if (eventList.size()>0) {
                    Log.d(TAG, "eventList main est rempli = " +eventList );
                    openBonsPlansActivity();
                }else {
                    Log.d(TAG, "eventList main is different 0 = " +eventList );
                }
            }*/

            /*if (eventList.isEmpty()) {
                Log.d(TAG, "eventList = " +eventList );

            }else {
                Log.d(TAG, "eventList = " +eventList );

            }*/

            // openBonsPlansActivity();

            // BonsPlansController bpc = new BonsPlansController(getApplicationContext());

            /*Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("BUNDLE");
            ArrayList <Event> Event = (ArrayList<Event>) bundle.getSerializable("BUNDLE") ;*/

            // ArrayList liste = getIntent().getParcelableArrayListExtra("eventList");

            // Log.d(TAG, "liste = " +Event );

            // openBonsPlansActivity();

            // List<Event> eventList = new ArrayList<>();

            /*BonsPlansController bpc = new BonsPlansController(getApplicationContext());
            Log.d(TAG, "bcp = " +bpc );

            ArrayList liste = bpc.getEventList();
            Log.d(TAG, "liste = " +liste );

            if (!liste.isEmpty()){
                // AJOUTER LE ALERTE DIALOG NEWSLETTER
                Log.d(TAG, "!liste.isEmpty = true " +liste );
            }else{
                openBonsPlansActivity();
                Log.d(TAG, "!liste.isEmpty = false " +liste );
            }*/
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

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    private void loadEvents()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayEventRequest = new JsonArrayRequest

                (Request.Method.GET, URL, null, response ->
                {
                    try {
                        // Browse request contain json
                        for (int i = 0; i < response.length(); i++) {

                            // Getting object from json array
                            JSONObject eventJsonObject = response.getJSONObject(i);

                            // CONCOURS
                            String concoursUrl = eventJsonObject.getString("concours_url");

                            //--------------------------
                            // RETRIEVE PREVENTES OBJECT
                            //--------------------------
                            int preventesNbr = 0;
                            int preventesPrix = 0;
                            String preventesUrl = "";
                            Preventes preventesEvent = new Preventes(preventesNbr, preventesPrix, preventesUrl);

                            if (eventJsonObject.has("preventes_event")) {
                                if (!eventJsonObject.isNull("preventes_event")) {
                                    JSONObject preventes = eventJsonObject.getJSONObject("preventes_event");
                                    if (preventes.has("nombre_preventes")) {
                                        if (!preventes.isNull("nombre_preventes")) {
                                            String newPreventesNbrString = preventes.getString("nombre_preventes");
                                            if (!TextUtils.isEmpty(newPreventesNbrString)) {
                                                int newPreventesNbr = Integer.parseInt(newPreventesNbrString);
                                                preventesEvent.setNombre(newPreventesNbr);
                                            } else {
                                            }
                                        } else {
                                        }
                                        if (preventes.has("prix_preventes")) {
                                            if (!preventes.isNull("prix_preventes")) {
                                                String newPreventesPrixString = preventes.getString("prix_preventes");
                                                if (!TextUtils.isEmpty(newPreventesPrixString)) {
                                                    int newPreventesPrix = Integer.parseInt(newPreventesPrixString);
                                                    preventesEvent.setPrix(newPreventesPrix);
                                                } else {
                                                }
                                            } else {
                                            }
                                            if (preventes.has("preventes_url")) {
                                                if (!preventes.isNull("preventes_url")) {
                                                    String newPreventesUrl = preventes.getString("preventes_url");
                                                    if (!TextUtils.isEmpty(newPreventesUrl)) {
                                                        preventesEvent.setPreventesUrl(newPreventesUrl);
                                                        Log.d(TAG, "newPreventesUrl = " +newPreventesUrl);
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
                            } else {
                            }

                            String pvUrlForTest = preventesEvent.getPreventesUrl();
                            Log.d(TAG, "pvUrlForTest = " +pvUrlForTest);

                            //------------------------------------
                            // TEST IF THEIR ARE BONS PLANS OR NOT
                            //------------------------------------
                            if (!TextUtils.isEmpty(concoursUrl) || !pvUrlForTest.isEmpty())
                            {
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

                                //-------------------------
                                // RETRIEVE ARTISTES OBJECT
                                //-------------------------

                                String name = "";
                                String facebook_url = "";

                                ArrayList<Artistes> artistesList = new ArrayList<>();

                                if (eventJsonObject.has("artistes_event")) {
                                    if (!eventJsonObject.isNull("artistes_event")) {
                                        // JSON ARRAY ARTISTES contient les 4 Artistes
                                        JSONArray jsonArrayArtistes = eventJsonObject.getJSONArray("artistes_event");

                                        for (int iA = 0; iA < jsonArrayArtistes.length(); iA++) {
                                            // JSON ARTISTE FROM list : artiste au format JSON OBJECT
                                            JSONObject jsonArtisteFromList = jsonArrayArtistes.getJSONObject(iA);
                                            Artistes artistesFromJsonObject = new Artistes(name, facebook_url);

                                            if (jsonArtisteFromList.has("name_artiste")) {
                                                String nameArtiste = jsonArtisteFromList.getString("name_artiste");
                                                artistesFromJsonObject.setName(nameArtiste);
                                                Log.d(TAG, "artistesFromJsonObject = " + artistesFromJsonObject);
                                            } else {
                                            }

                                            if (jsonArtisteFromList.has("facebook_url_artiste")) {
                                                String artisteFacebookUrl = jsonArtisteFromList.getString("facebook_url_artiste");
                                                artistesFromJsonObject.setFacebookUrl(artisteFacebookUrl);
                                                Log.d(TAG, "artistesFromJsonObject = " + artistesFromJsonObject);
                                            } else {
                                            }

                                            artistesList.add(artistesFromJsonObject);
                                            Log.d(TAG, "artistesList = " + artistesList);
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

                                Lieux lieuxEvent = new Lieux(photoUrlLieux, nameLieux, adresseLieux, mapLieuIframeLieux,
                                        facebookUrlLieux, siteUrlLieux);

                                if (eventJsonObject.has("lieux_event")) {
                                    if (!eventJsonObject.isNull("lieux_event")) {
                                        JSONObject lieux = eventJsonObject.getJSONObject("lieux_event");
                                        if (lieux.has("name_lieux")) {
                                            if (!lieux.isNull("name_lieux")) {
                                                String newNameLieux = lieux.getString("name_lieux");
                                                lieuxEvent.setNameLieux(newNameLieux);
                                            } else {
                                            }
                                        }
                                        if (lieux.has("iframe_url_lieux")) {
                                            if (!lieux.isNull("iframe_url_lieux")) {
                                                String newIframeUrlLieux = lieux.getString("iframe_url_lieux");
                                                lieuxEvent.setMapLieuIframe(newIframeUrlLieux);
                                            } else {
                                            }
                                        }
                                        if (lieux.has("name_lieux")) {
                                            if (!lieux.isNull("name_lieux")) {
                                                String newNameLieux = lieux.getString("name_lieux");
                                                lieuxEvent.setNameLieux(newNameLieux);
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
                                Event event = new Event(photoUrlEvent, nameEvent, dateStartEvent, dateEndEvent,
                                        facebookUrlEvent, preventesEvent, artistesList, lieuxEvent, concoursUrl);

                                eventList.add(event);

                                // openBonsPlansActivity();

                                // BonsPlansModel.setListe(eventList);

                                /*// creating adapter object and setting it to recyclerview
                                BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                                recylcerViewBonsPlansList.setAdapter(adapter);*/
                            }else {
                                Log.d(TAG, "eventList 11111" );
                                // openBonsPlansActivity();
                            }
                            Log.d(TAG, "eventList 22222 = bonsplans ajoutés à la liste" );
                        }
                        Log.d(TAG, "eventList 33333" );

                    } catch (JSONException e) {
                        Log.d(TAG, "eventList 55555" );

                        e.printStackTrace();
                    } catch (ParseException e) {
                        Log.d(TAG, "eventList 66666" );

                        e.printStackTrace();
                    }
                    Log.d(TAG, "eventList 77777" );

                },
                        error -> {
                            Log.d(TAG, "eventList 88888" );

                            // Do something when error occurred
                        }
                );
        Log.d(TAG, "eventList 99999" );
        requestQueue.add(jsonArrayEventRequest);
        Log.d(TAG, "eventList 10101010" );

        /*ArrayList list = new ArrayList(Collections.singleton(jsonArrayEventRequest));

        int i = list.size();

        Log.d(TAG, "i = " +i);*/

        /*Log.d(TAG,"jsonArrayEventRequest = " + jsonArrayEventRequest);
        byte[] str = jsonArrayEventRequest.getBody();
        Log.d(TAG,"str = " + str);*/

        /*if (str.) {
           Log.d(TAG,"str <0 = " + str);
        }else{
            Log.d(TAG,"str >0 = " + str);
        }*/


        /*Cache.Entry cache = jsonArrayEventRequest.getCacheEntry();

        Log.d(TAG,"cacheEntry = " + cache);

        if (cache.isExpired()) {
            Log.d(TAG,"cacheEntry expired = " + cache);
        }
        else {
            Log.d(TAG,"cacheEntry not expired =  " + cache);

        }*/
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
