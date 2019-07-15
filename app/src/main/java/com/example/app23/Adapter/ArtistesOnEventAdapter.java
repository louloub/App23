package com.example.app23.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app23.Activity.ArtistesPageActivity;
import com.example.app23.Object.Artistes;
import com.example.app23.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.ContextCompat.*;

public class ArtistesOnEventAdapter extends RecyclerView.Adapter<ArtistesOnEventAdapter.ArtistesEventViewHolder>  {

    // JSON Source
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes4.json";
    /*// List of artistes on BDD
    private List<Artistes> artistesList;*/
    
    // List of artistes on event
    private List<Artistes> artistesEventList;
    // Context
    private Context mCtx;
    // Recycler view for display artiste page
    RecyclerView recyclerView;

    // Color for background artiste if it's clickable
    int colorString = 0xFFA1C332;

    // Counstructor
    public ArtistesOnEventAdapter(Context mCtx, List<Artistes> artistesEventList) {
        this.mCtx = mCtx;
        this.artistesEventList = artistesEventList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ArtistesEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        // Inflate the layout view you have created for the list rows here
        View view = inflater.inflate(R.layout.artistes_event_list, null);
        return new ArtistesEventViewHolder (view);
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(ArtistesEventViewHolder holder, int i) {
        Artistes artistes = artistesEventList.get(i);
        String artisteNameArtisteEvent = artistes.getName();
        holder.tvArtistesNameOnEvent.setText(artisteNameArtisteEvent);

        // Color Background if artiste is on the website (& clickable)
        loadArtistesForChangeBackground(artisteNameArtisteEvent,holder);

        //------------------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON ARTISTE IF REGISTER IN YOURDJ
        //------------------------------------------------------------

        holder.tvArtistesNameOnEvent.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, ArtistesPageActivity.class);
            loadArtistes(artisteNameArtisteEvent);
        });
    }

    @Override
    public int getItemCount() {
        return artistesEventList.size();
    }

    // This is your ViewHolder class that helps to populate data to the view
    class ArtistesEventViewHolder extends RecyclerView.ViewHolder {

        TextView tvArtistesNameOnEvent;
        RecyclerView recyclerView;

        ArtistesEventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArtistesNameOnEvent = (TextView) itemView.findViewById(R.id.tvArtistesNameOnEvent);
            recyclerView = itemView.findViewById(R.id.recylcerView);
        }
    }

    //------------------------------------
    // LOAD ARTISTES FROM JSON FOR DISPLAY
    //------------------------------------
    public void loadArtistes(String nameArtisteFromEventToTest)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);

        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, response ->
                {
                    try {
                        // Browse request contain json
                        for (int i = 0; i < response.length(); i++) {

                            // Getting object from json array
                            JSONObject artistesJsonObject = response.getJSONObject(i);

                            String name = artistesJsonObject.getString("name");

                            if (name.equals(nameArtisteFromEventToTest))
                            {
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

                                Intent intent = new Intent(mCtx, ArtistesPageActivity.class);
                                intent.putExtra("artiste", artiste);

                                mCtx.startActivity(intent);
                            }
                            else {

                            }
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

    //------------------------------------------------------------
    // LOAD ARTISTES FROM JSON FOR CHANGE BACKGROUND IF ON WEBSITE
    //------------------------------------------------------------
    public void loadArtistesForChangeBackground(String nameArtisteFromEventToTest, ArtistesEventViewHolder holder)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);

        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, response ->
                {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject artistesJsonObject = response.getJSONObject(i);
                            String name = artistesJsonObject.getString("name");
                            if (name.equals(nameArtisteFromEventToTest))
                            {
                                // SET COLOR OF BACKGROUND IF ARTISTE IS ON WEBSITE
                                holder.tvArtistesNameOnEvent.setBackgroundColor(colorString);
                            }
                            else {
                            }
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
        // return false;
    }
}