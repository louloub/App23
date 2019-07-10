package com.example.app23.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app23.Activity.EventListActivity;
import com.example.app23.Activity.EventPageActivity;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.Object.Lieux;
import com.example.app23.Object.Preventes;
import com.example.app23.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>  {

    public static String FACEBOOK_URL_FOR_SHARING = "https://www.yourdj.fr";

    private Context mCtx;
    private List<Event> eventList;

    public EventAdapter(EventListActivity mCtx, List<Event> eventList) {
        this.mCtx = mCtx;
        this.eventList = eventList;
    }

    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.event_list_model, null);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // PHOTO
        String photo = event.getPhotoUrl();

        //--------------------------------------------------
        // RECUPERATION & AFFICHAGE DE LA PHOTO AVEC SON URL
        //--------------------------------------------------
        Glide.with(mCtx)
                .load(event.getPhotoUrl())
                .into(holder.ivPhotoEvent);

        //-----
        // NAME
        //-----
        String name = event.getName();

        //-----
        // DATE
        //-----
        Date dateStart = event.getDateStart();
        DateFormat formatterDateStart = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        String dateStartString = formatterDateStart.format(dateStart);

        Date dateEnd = event.getDateEnd();
        DateFormat formatterDateEnd = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        String dateEndString = formatterDateEnd.format(dateEnd);

        //-------------
        // FACEBOOK URL
        //-------------
        String facebook = event.getFacebookUrl();

        //----------
        // PREVENTES
        //----------
        Preventes preventes = event.getPreventes();
        int prixPreventesInt = preventes.getPrix();
        String prixPreventesString = Integer.toString(prixPreventesInt);
        int nbrPreventesInt = preventes.getNombre();
        String nbrPreventesString = Integer.toString(nbrPreventesInt);
        String preventesUrl = preventes.getPreventesUrl();

        //---------
        // ARTISTES
        //---------
        ArrayList<Artistes> artistesFromList = event.getArtistes();

        //-----------------
        // ADAPTER ARTISTES
        //-----------------
        ArtistesOnEventAdapter artistesAdapter = new ArtistesOnEventAdapter(mCtx,artistesFromList);
        holder.recyclerViewArtistesEventList.setAdapter(artistesAdapter);

        //------
        // LIEUX
        //------
        Lieux lieux = event.getLieux();
        String nomLieux = lieux.getName();
        String iFrameLieux = lieux.getMapLieuIframe();
        Log.d(TAG, "iFrameLieux = " +iFrameLieux);

        //---------
        // CONCOURS
        //---------
        String concoursUrl = event.getConcoursUrl();

        //---------------------------------
        // HOLDER / DISPLAY IMAGE IF LINKED
        //---------------------------------
        if (photo.isEmpty()) {
            holder.ivPhotoEvent.setVisibility(GONE);
        }
        else {}

        if (facebook.isEmpty()) {
            holder.ivFacebookEvent.setVisibility(GONE);
        }
        else {}

        //-------------
        // SET CONTENT
        //-------------
        holder.tvNameEvent.setText(name);
        holder.tvDateStart.setText(dateStartString);
        holder.tvDateEnd.setText(dateEndString);
        // holder.tvArtisteName.setText("test test test");
        holder.tvLieux.setText(nomLieux);

        //----------
        // PREVENTES
        //----------
        holder.btnPreventes.setVisibility(View.VISIBLE);
        if (nbrPreventesInt==0) {
            holder.btnPreventes.setVisibility(GONE);
        } else {
            holder.btnPreventes.setText("Il reste " + nbrPreventesString + " places à " + prixPreventesString + " €");
            holder.btnPreventes.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogPreventes = new AlertDialog.Builder(mCtx);
                alertDialogPreventes.setTitle("Choppe ta prévente !");

                //--------------
                // START WEBVIEW
                //--------------
                String iframe = "<!DOCTYPE html>" +
                        "<html>" +
                            "<body>" +
                                "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                                "src=\""+preventesUrl+"\">" +
                                "</iframe>" +
                            "</body>" +
                        "</html>";

                WebView webViewPreventes = new WebView(mCtx);
                webViewPreventes.getSettings().setJavaScriptEnabled(true);
                webViewPreventes.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
                webViewPreventes.setWebViewClient(new WebViewClient(){
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
                webViewPreventes.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");

                //--------------
                // END WEBVIEW
                //--------------

                //--------------
                // ALERT DIALOG
                //--------------
                alertDialogPreventes.setView(webViewPreventes);
                alertDialogPreventes.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogPreventes.show();
            });
        }

        //------------------------------------
        // LISTENER FOR WHEN WE CLICK ON PLACE
        //------------------------------------
            holder.tvLieux.setOnClickListener(v -> {
                if (!iFrameLieux.isEmpty())
                {
                    AlertDialog.Builder alertDialogLieux = new AlertDialog.Builder(mCtx);
                    alertDialogLieux.setTitle("Ca se passe ici !");

                    //--------------
                    // START WEBVIEW
                    //--------------
                    String iframe = "<!DOCTYPE html>" +
                            "<html>" +
                            "<body>" +
                            "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                            "src=\"" + iFrameLieux + "\">" +
                            "</iframe>" +
                            "</body>" +
                            "</html>";

                    WebView webViewPreventes = new WebView(mCtx);
                    webViewPreventes.getSettings().setJavaScriptEnabled(true);
                    webViewPreventes.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
                    webViewPreventes.setWebViewClient(new WebViewClient() {
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
                    webViewPreventes.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");

                    //--------------
                    // END WEBVIEW
                    //--------------

                    //--------------
                    // ALERT DIALOG
                    //--------------
                    alertDialogLieux.setView(webViewPreventes);
                    alertDialogLieux.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogLieux.show();
                }
            });

        //------------------
        // CONTEST VISIBILIY
        //------------------
        holder.btnConcours.setVisibility(View.VISIBLE);
        if (concoursUrl.isEmpty())
        {
            holder.btnConcours.setVisibility(View.GONE);
        } else {
        }

        //---------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON CONTEST BUTTON
        //---------------------------------------------
        holder.btnConcours.setOnClickListener(v ->
        {
            Event eventForIntent = new Event(photo,name,concoursUrl);

            Intent intent = new Intent(mCtx, EventPageActivity.class);
            intent.putExtra("event", eventForIntent);

            mCtx.startActivity(intent);
        });

        //--------------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON EVENT SOCIAL NETWORK ICON
        //--------------------------------------------------------
        /*
        holder.ivFacebookEvent.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebook));
            mCtx.startActivity(intent);
        });
        */

        //-----------------
        // BUTTON ANIMATION
        //-----------------
        Animation animation = AnimationUtils.loadAnimation(mCtx, R.anim.alpha);

        holder.ivFacebookEvent.setOnClickListener(v -> {
            holder.ivFacebookEvent.startAnimation(animation);
            // String facebookUrl = artiste.getFacebookUrl();

            getOpenFacebookIntent(mCtx.getPackageManager(), facebook);
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrlApp = getFacebookURL(mCtx, facebook);
            facebookIntent.setData(Uri.parse(facebookUrlApp));
            mCtx.startActivity(facebookIntent);
        });

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON EVENT IN THE LIST
        //-------------------------------------------------
        /*holder.itemView.setOnClickListener(v -> {

            Event eventForIntent = new Event(photo,name,dateStart,dateEnd,facebook,preventes,artistesFromList,lieux, concoursUrl);

            Intent intent = new Intent(mCtx, EventPageActivity.class);
            intent.putExtra("event", eventForIntent);

            mCtx.startActivity(intent);
        });*/
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameEvent, tvDateStart, tvDateEnd, tvArtisteName, tvLieux;
        ImageView ivPhotoEvent, ivFacebookEvent;
        Button btnPreventes, btnConcours;
        RecyclerView recyclerViewArtistesEventList;

        public EventViewHolder(View itemView) {
            super(itemView);

            ivPhotoEvent = itemView.findViewById(R.id.ivPhotoEvent);
            tvNameEvent = itemView.findViewById(R.id.tvNameEvent);
            tvDateStart = itemView.findViewById(R.id.tvDateStart);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            ivFacebookEvent = itemView.findViewById(R.id.ivFacebookEvent);
            // tvArtisteName = itemView.findViewById(R.id.tvArtisteName);
            tvLieux = itemView.findViewById(R.id.tvLieux);
            btnPreventes = itemView.findViewById(R.id.btnPreventes);
            btnConcours = itemView.findViewById(R.id.btnConcours);
            recyclerViewArtistesEventList = itemView.findViewById(R.id.recyclerViewArtistesEventList);
            // Affichage horizontal des artistes dans la liste des événements
            recyclerViewArtistesEventList.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL, false));
            // Affichage d'une bar entre chaque artistes de la liste
            recyclerViewArtistesEventList.addItemDecoration(new DividerItemDecoration(itemView.getContext(), DividerItemDecoration.HORIZONTAL));

            // Test supprimer la dernière bar aprés le dernier artiste (fonctionne pas)
            /*RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(itemView.getContext(), R.drawable.divider));
            recyclerViewArtistesEventList.addItemDecoration(dividerItemDecoration);*/

        }
    }

    private class WebViewResizer {
        @JavascriptInterface
        public void processHeight(String height) {
            // height is in DP units. Convert it to PX if you are adjusting the WebView's height.
            // height could be 0 if WebView visibility is Visibility.GONE.
            // If changing the WebView height, do it on the main thread!
        }
    }

    //----------------------------------------
    // ALERTDIALOG FOR CLICK ON CONTEST BUTTON
    //----------------------------------------

    public void alertDialogConcours()
    {
        // Setup Alert builder
        android.support.v7.app.AlertDialog.Builder myPopup = new android.support.v7.app.AlertDialog.Builder(mCtx);
        myPopup.setTitle("Consignes du concours YourDJ !");
        myPopup.setMessage("Message");

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

        // create and show the alert dialog
        android.support.v7.app.AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    //----------------------
    // INTENT FB APPLICATION
    //----------------------
    public static Intent getOpenFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    // method to get the right URL to use in the intent
    public static String getFacebookURL(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // newer versions of fb app
                return "fb://facewebmodal/f?href=" + url;
            } else { // older versions of fb app
                return "fb://page/" + url;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return url; // normal web url
        }
    }
}