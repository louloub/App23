package com.example.app23.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Adapter.BonsPlansAdapter;
import com.example.app23.Adapter.EventAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.Object.Lieux;
import com.example.app23.Object.Preventes;
import com.example.app23.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.android.volley.VolleyLog.TAG;

public class BonsPlansListActivity extends OptionMenuActivity {

    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event3.json";
    private static final String TAG = "BonsPlansListActivity" ;
    private static final String NAME_FOR_ACTIONBAR = "Bons Plans";
    private Context mContext;
    List<Event> eventList;
    RecyclerView recylcerViewBonsPlansList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bons_plans);
        eventList = new ArrayList<>();
        mContext = getApplicationContext();
        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);
        recylcerViewBonsPlansList = findViewById(R.id.recylcerViewBonsPlansList);
        recylcerViewBonsPlansList.setHasFixedSize(true);
        recylcerViewBonsPlansList.setLayoutManager(new LinearLayoutManager(this));
        loadEvents();

        int eventListSize = eventList.size();

        if (eventListSize == 0){
            Log.d(TAG, "eventListSize" +eventListSize);
            alertDialogNoBonsPlans();
        }else{
        }
    }

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    private void loadEvents()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayEventRequest = new JsonArrayRequest


        /*if (response.length()==0)
        {
            alertDialogNoBonsPlans();
        }*/

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

                                /*ArtistesOnEventAdapter artistesAdapter = new ArtistesOnEventAdapter(EventListActivity.this,artistesList);
                                recyclerViewArtistesEventList.setAdapter(artistesAdapter);*/

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

                                // creating adapter object and setting it to recyclerview
                                BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                                recylcerViewBonsPlansList.setAdapter(adapter);
                            }else {
                            }
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

    public void alertDialogNoBonsPlans()
    {
        // Setup Alert builder
        android.support.v7.app.AlertDialog.Builder myPopup = new android.support.v7.app.AlertDialog.Builder(this);
        myPopup.setTitle("Pas encore de bons plans disponibles");
        myPopup.setMessage("Tu peux t'inscrire à notre newsletter ou nous suivre sur Facebook pour ne rien rater :");

        /*// Ddd a radio button list
        String[] villes = {"Montpellier", "Toulouse", "Marseille", "Bordeaux", "Nantes"};
        int checkedItems = 0;
        myPopup.setSingleChoiceItems(villes, checkedItems, (dialog, which) -> {
        });
        myPopup.setPositiveButton("Valider", (dialogInterface, i) -> {
            ListView lw = ((android.support.v7.app.AlertDialog)dialogInterface).getListView();
            Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
            Toast.makeText(getApplicationContext(), "Tu as choisi " + checkedItem, Toast.LENGTH_LONG).show();
            // TODO : utiliser "checkedItem" pour le choix du contenu par ville
        });

        *//*myPopup.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Vous avez cliquez sur Non", Toast.LENGTH_SHORT).show();
            }
        });*/

        myPopup.setCancelable(false);
        myPopup.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BonsPlansListActivity.super.onBackPressed();
            }
        });
        // create and show the alert dialog
        android.support.v7.app.AlertDialog dialog = myPopup.create();
        myPopup.show();
    }
}