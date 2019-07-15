package com.example.app23.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BonsPlansListActivity extends OptionMenuActivity {

    // JSON avec 17 events, dont des bons plans :
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event2.json";
    // JSON sans bons plans :
    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/event4.json";

    private static final String TAG = "BonsPlansListActivity" ;
    private static final String NAME_FOR_ACTIONBAR = "Bons Plans";
    private Context mContext;
    ArrayList<Event> eventList;
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

                                if (!eventList.isEmpty()) {
                                    // creating adapter object and setting it to recyclerview
                                    BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                                    recylcerViewBonsPlansList.setAdapter(adapter);
                                }else {
                                }
                            }else {
                            }
                        }
                        if (eventList.isEmpty()) {
                            // creating adapter object and setting it to recyclerview
                            BonsPlansAdapter adapter = new BonsPlansAdapter(BonsPlansListActivity.this, eventList);
                            recylcerViewBonsPlansList.setAdapter(adapter);
                            alertDialogNoBonsPlans();
                        }else {
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
        myPopup.setMessage("Tu peux t'inscrire à notre newsletter pour recevoir un mail par semaine avec tous les bons plans :");

        /*String iframe = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                "src=\"" + codeHtmlNewsletter + "\">" +
                "</iframe>" +
                "</body>" +
                "</html>";*/

        WebView webViewNewsletter = new WebView(getApplicationContext());
        webViewNewsletter.getSettings().setJavaScriptEnabled(true);
        webViewNewsletter.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
        webViewNewsletter.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                // webView.loadUrl("javascript:window.WebViewResizer.processHeight(document.querySelector('body').offsetHeight);");
                super.onPageFinished(webView, url);
            }

            private boolean loadUrl(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                // Otherwise allow the OS to handle it
                else if (url.startsWith("tel:")) {
                    Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    // startActivity(tel);
                    return true;
                }
                return true;
            }
        });
        webViewNewsletter.loadDataWithBaseURL("", codeHtmlNewsletter, "text/html", "UTF-8", "");
        myPopup.setView(webViewNewsletter);
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

    private class WebViewResizer {
        @JavascriptInterface
        public void processHeight(String height) {
            // height is in DP units. Convert it to PX if you are adjusting the WebView's height.
            // height could be 0 if WebView visibility is Visibility.GONE.
            // If changing the WebView height, do it on the main thread!
        }
    }

    String codeHtmlNewsletter =
            "<!-- Begin Mailchimp Signup Form -->\n" +
                    "<link href=\"//cdn-images.mailchimp.com/embedcode/classic-10_7.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                    "<style type=\"text/css\">\n" +
                    "#mc_embed_signup{background:#fff; clear:left; font:14px Helvetica,Arial,sans-serif; border:1px}\n" +
                    "ul#ul li {\n" +
                    "    list-style-type: none;\n" +
                    "\n" +
                    "   }\n" +
                    "\n" +
                    "\t/* Add your own Mailchimp form style overrides in your site stylesheet or in this style block.\n" +
                    "\t   We recommend moving this block and the preceding CSS link to the HEAD of your HTML file.\n" +
                    "\t   list-style-position: outside;\n" +
                    "\n" +
                    "   display: inline;\n" +
                    "   padding: 2px ;\n" +
                    "   margin: 0;\n" +
                    "   width: 19%; */\n" +
                    "</style>\n" +
                    "<div id=\"mc_embed_signup\">\n" +
                    "<form action=\"https://yourdj.us5.list-manage.com/subscribe/post?u=d0b2aaee0796a328290834c4a&amp;id=445e00e010\" class=\"validate\" id=\"mc-embedded-subscribe-form\" method=\"post\" name=\"mc-embedded-subscribe-form\" novalidate=\"\" target=\"_blank\">\n" +
                    "<div id=\"mc_embed_signup_scroll\">\n" +
                    "<h2>Newsletter Hebdomadaire YourDJ</h2>\n" +
                    "\n" +
                    "<div class=\"mc-field-group\"><label for=\"mce-EMAIL\">Adresse Email <span class=\"asterisk\">*</span> </label> <input class=\"required email\" id=\"mce-EMAIL\" name=\"EMAIL\" type=\"email\" value=\"\" /></div>\n" +
                    "\n" +
                    "<div class=\"mc-field-group\"><label for=\"mce-FNAME\">Prénom </label> <input id=\"mce-FNAME\" name=\"FNAME\" type=\"text\" value=\"\" /></div>\n" +
                    "\n" +
                    "<div class=\"mc-field-group\"><label for=\"mce-LNAME\">Nom </label> <input id=\"mce-LNAME\" name=\"LNAME\" type=\"text\" value=\"\" /></div>\n" +
                    "\n" +
                    "<div class=\"mc-field-group input-group\"><strong>Pour quelle ville veux tu recevoir des bons plans ? </strong><br /></div>\n" +
                    "&nbsp;\n" +
                    "<ul id=\"ul\">\n" +
                    "\t<li><input name=\"group[18949][1]\" type=\"checkbox\" value=\"1\" /><label for=\"mce-group[18949]-18949-0\"> &nbsp;Montpellier</label></li>\n" +
                    "\t<li><input name=\"group[18949][2]\" type=\"checkbox\" value=\"2\" /><label for=\"mce-group[18949]-18949-1\"> &nbsp;Toulouse</label></li>\n" +
                    "\t<li><input name=\"group[18949][4]\" type=\"checkbox\" value=\"4\" /><label for=\"mce-group[18949]-18949-2\"> &nbsp;Marseille</label></li>\n" +
                    "\t<li><input name=\"group[18949][8]\" type=\"checkbox\" value=\"8\" /><label for=\"mce-group[18949]-18949-3\"> &nbsp;Bordeaux</label></li>\n" +
                    "\t<li><input name=\"group[18949][16]\" type=\"checkbox\" value=\"16\" /><label for=\"mce-group[18949]-18949-4\">&nbsp;Nantes</label></li>\n" +
                    "</ul>\n" +
                    "\n" +
                    "\n" +
                    "<div class=\"clear\" id=\"mce-responses\">\n" +
                    "<div class=\"response\" id=\"mce-error-response\" style=\"display:none\">&nbsp;</div>\n" +
                    "\n" +
                    "<div class=\"response\" id=\"mce-success-response\" style=\"display:none\">&nbsp;</div>\n" +
                    "</div>\n" +
                    "<!-- real people should not fill this in and expect good things - do not remove this or risk form bot signups-->\n" +
                    "\n" +
                    "<div aria-hidden=\"true\" style=\"position: absolute; left: -5000px;\"><input name=\"b_d0b2aaee0796a328290834c4a_445e00e010\" tabindex=\"-1\" type=\"text\" value=\"\" /></div>\n" +
                    "\n" +
                    "<div class=\"clear\"><input class=\"button\" id=\"mc-embedded-subscribe\" name=\"subscribe\" type=\"submit\" value=\"S'inscrire\" /></div>\n" +
                    "</div>\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "<script type='text/javascript' src='//s3.amazonaws.com/downloads.mailchimp.com/js/mc-validate.js'></script><script type='text/javascript'>(function($) {window.fnames = new Array(); window.ftypes = new Array();fnames[0]='EMAIL';ftypes[0]='email';fnames[1]='FNAME';ftypes[1]='text';fnames[2]='LNAME';ftypes[2]='text'; /*\n" +
                    " * Translated default messages for the $ validation plugin.\n" +
                    " * Locale: FR\n" +
                    " */\n" +
                    "$.extend($.validator.messages, {\n" +
                    "        required: \"Ce champ est requis.\",\n" +
                    "        remote: \"Veuillez remplir ce champ pour continuer.\",\n" +
                    "        email: \"Veuillez entrer une adresse email valide.\",\n" +
                    "        url: \"Veuillez entrer une URL valide.\",\n" +
                    "        date: \"Veuillez entrer une date valide.\",\n" +
                    "        dateISO: \"Veuillez entrer une date valide (ISO).\",\n" +
                    "        number: \"Veuillez entrer un nombre valide.\",\n" +
                    "        digits: \"Veuillez entrer (seulement) une valeur numérique.\",\n" +
                    "        creditcard: \"Veuillez entrer un numéro de carte de crédit valide.\",\n" +
                    "        equalTo: \"Veuillez entrer une nouvelle fois la même valeur.\",\n" +
                    "        accept: \"Veuillez entrer une valeur avec une extension valide.\",\n" +
                    "        maxlength: $.validator.format(\"Veuillez ne pas entrer plus de {0} caractères.\"),\n" +
                    "        minlength: $.validator.format(\"Veuillez entrer au moins {0} caractères.\"),\n" +
                    "        rangelength: $.validator.format(\"Veuillez entrer entre {0} et {1} caractères.\"),\n" +
                    "        range: $.validator.format(\"Veuillez entrer une valeur entre {0} et {1}.\"),\n" +
                    "        max: $.validator.format(\"Veuillez entrer une valeur inférieure ou égale à {0}.\"),\n" +
                    "        min: $.validator.format(\"Veuillez entrer une valeur supérieure ou égale à {0}.\")\n" +
                    "});}(jQuery));var $mcj = jQuery.noConflict(true);</script><!--End mc_embed_signup-->";

}