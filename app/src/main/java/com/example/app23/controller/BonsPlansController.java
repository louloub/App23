package com.example.app23.controller;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Activity.BonsPlansListActivity;
import com.example.app23.Activity.MainActivity;
import com.example.app23.Adapter.BonsPlansAdapter;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.Object.Lieux;
import com.example.app23.Object.Preventes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.android.volley.VolleyLog.TAG;

public class BonsPlansController {

    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event2.json";
    private static ArrayList<Event> eventList = new ArrayList<>();

    public ArrayList<Event> getEventList() {
        return eventList;
    }
    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public BonsPlansController(Context applicationContext) {

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);

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

                                /*Log.d(TAG, "eventList = " +eventList );

                                Intent intent = new Intent(this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("eventList", (Serializable)eventList);
                                intent.putExtra("BUNDLE", bundle);
                                */

                                // creating adapter object and setting it to recyclerview
                                /*BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                                recylcerViewBonsPlansList.setAdapter(adapter);*/

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

    }


    // private Context mContext;

    /*protected void onCreate(Bundle savedInstanceState) {

        // eventList = new ArrayList<>();
        mContext = getApplicationContext();
        loadEvents();

    }*/
    /*public BonsPlansController()
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

                                // creating adapter object and setting it to recyclerview
                                *//*BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                                recylcerViewBonsPlansList.setAdapter(adapter);*//*
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
    }*/
