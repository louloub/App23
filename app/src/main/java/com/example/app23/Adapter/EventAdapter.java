package com.example.app23.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>  {

    // public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/YourDJToulouse/posts/2246241378778090";
    // public static String FACEBOOK_URL_FOR_SHARING = "fb://page/?id=258326807569567";
    // public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/Bejiines/photos/a.386011438160632/2492243550870733/?type=3&theater&ifg=1";
    // public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/YourDJToulouse/photos/a.295593667176214/2270758406326387/?type=3&theater";
    // public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/MIND.SoundVector/posts/2811124258961306";
    public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/YourDJToulouse/posts/2246241378778090";

    // public static String FACEBOOK_URL_FOR_SHARING = "https://www.facebook.com/sharer/sharer.php?u=";

    // TODO : comment gérer le changement de ville pour cette constante ?
    public static String FACEBOOK_PAGE_ID = "YourDJMontpellier";

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

        String photo = event.getPhotoUrl();
        String name = event.getName();

        // DATE
        Date dateStart = event.getDateStart();
        DateFormat formatterDateStart = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        String dateStartString = formatterDateStart.format(dateStart);

        Date dateEnd = event.getDateEnd();
        DateFormat formatterDateEnd = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        String dateEndString = formatterDateEnd.format(dateEnd);

        String facebook = event.getFacebookUrl();

        // PREVENTES
        Preventes preventes = event.getPreventes();
        int prixPreventesInt = preventes.getPrix();
        String prixPreventesString = Integer.toString(prixPreventesInt);
        int nbrPreventesInt = preventes.getNombre();
        String nbrPreventesString = Integer.toString(nbrPreventesInt);
        String preventesUrl = preventes.getPreventesUrl();

        // ARTISTES
        Artistes artistes = (Artistes) event.getArtistes();
        // TODO : afficher plusieurs artistes avec un loop
        String artisteName = artistes.getName();
        // TODO : rendre le DJ cliquable si il est sur notre site

        // LIEUX
        Lieux lieux = event.getLieux();
        String nomLieux = lieux.getName();

        // CONCOURS
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

        //--------------------------------------
        // RECUPERATION DE LA PHOTO AVEC SON URL
        //--------------------------------------
        Glide.with(mCtx)
                .load(event.getPhotoUrl())
                .into(holder.ivPhotoEvent);

        //-------------
        // SET CONTENT
        //-------------
        holder.tvNameEvent.setText(name);
        holder.tvDateStart.setText(dateStartString);
        holder.tvDateEnd.setText(dateEndString);
        holder.tvArtisteName.setText(artisteName);
        holder.tvLieux.setText(nomLieux);

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

        holder.btnConcours.setVisibility(View.VISIBLE);
        if (concoursUrl.isEmpty()) {
            holder.btnConcours.setVisibility(View.GONE);
        } else {

        }

        //---------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON CONTEST BUTTON
        //---------------------------------------------
        holder.btnConcours.setOnClickListener(v ->
        {
            Event eventForIntent = new Event(photo,name,dateStart,dateEnd,facebook,preventes,artistes,lieux,concoursUrl);

            Intent intent = new Intent(mCtx, EventPageActivity.class);
            intent.putExtra("event", eventForIntent);

            mCtx.startActivity(intent);
        });

        //--------------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON EVENT SOCIAL NETWORK ICON
        //--------------------------------------------------------
        holder.ivFacebookEvent.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebook));
            mCtx.startActivity(intent);
        });

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON EVENT IN THE LIST
        //-------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

            Event eventForIntent = new Event(photo,name,dateStart,dateEnd,facebook,preventes,artistes,lieux, concoursUrl);

            Intent intent = new Intent(mCtx, EventPageActivity.class);
            intent.putExtra("event", eventForIntent);

            mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameEvent, tvDateStart, tvDateEnd, tvArtisteName, tvLieux;
        ImageView ivPhotoEvent, ivFacebookEvent;
        Button btnPreventes, btnConcours;

        public EventViewHolder(View itemView) {
            super(itemView);

            ivPhotoEvent = itemView.findViewById(R.id.ivPhotoEvent);
            tvNameEvent = itemView.findViewById(R.id.tvNameEvent);
            tvDateStart = itemView.findViewById(R.id.tvDateStart);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            ivFacebookEvent = itemView.findViewById(R.id.ivFacebookEvent);
            tvArtisteName = itemView.findViewById(R.id.tvArtisteName);
            tvLieux = itemView.findViewById(R.id.tvLieux);
            btnPreventes = itemView.findViewById(R.id.btnPreventes);
            btnConcours = itemView.findViewById(R.id.btnConcours);
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

    /*//----------------------
    // INTENT FB APPLICATION
    //----------------------
    public static Intent getOpenFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/sharer/sharer.php?u=" + url);
            }

        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    // method to get the right URL to use in the intent
    public String getConcoursFacebookURL(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // newer versions of fb app
                return "fb://facewebmodal/f?href=https://www.facebook.com/sharer/sharer.php?u=" + url;
            } else { // older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Todo : quelle URL entrer dans la constante pour ce cas de figure ?
            return FACEBOOK_URL_FOR_SHARING; // normal web url
        }
    }*/

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
}