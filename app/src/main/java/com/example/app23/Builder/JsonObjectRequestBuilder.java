/*
package com.example.app23.Builder;

import android.support.v7.widget.RecyclerView;

import com.example.app23.Activity.ArtistesListActivity;
import com.example.app23.Adapter.ArtistesAdapter;
import com.example.app23.Object.Artistes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewArtistesBuilder {

    public static RecyclerView getRecyclerViewFromRequest (JSONObject response, List<Artistes> artistesList,
                                                           RecyclerView recyclerView;)
    {
        // TODO: Move to static class builder that return the builded object
        JSONArray jsonArrayArtistesAPI = (JSONArray) response.get("results");

        for (int i = 0; i < jsonArrayArtistesAPI.length(); i++)
        {
            artistesList.add(JsonArtistesObjectsBuilder.getArtistesObjectsFromAPI(jsonArrayArtistesAPI,i));
        }

        if (recyclerView.getAdapter() == null) {
            ArtistesAdapter adapter = new ArtistesAdapter(this, artistesList);
            recyclerView.setAdapter(adapter);
        }

        // le ELSE ajoute les nouveaux items à le suite des derniers items
        else {
            recyclerView.getAdapter().notifyItemInserted(artistesList.size());
        }
    }
}*/
