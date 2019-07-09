package com.example.app23.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app23.Activity.ArtistesEventActivity;
import com.example.app23.Activity.EventListActivity;
import com.example.app23.Object.Artistes;
import com.example.app23.Object.Event;
import com.example.app23.R;

import java.util.List;

public class ArtistesEventAdapter extends RecyclerView.Adapter<ArtistesEventAdapter.ArtistesEventViewHolder>  {

    private Context mCtx;
    private List<Artistes> artistesEventList;

    // Counstructor for the Class
    public ArtistesEventAdapter(Context mCtx, List<Artistes> artistesEventList) {
        this.mCtx = mCtx;
        this.artistesEventList = artistesEventList;
    }

    /*public ArtistesEventAdapter(ArtistesEventActivity mCtx, List<Artistes> artistesEventList) {
        this.mCtx = mCtx;
        this.artistesEventList = artistesEventList;
    }*/

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

        String name = artistes.getName();
        String url = artistes.getFacebookUrl();

        holder.tvArtistesNameOnEvent.setText(name);
    }

    @Override
    public int getItemCount() {
        return artistesEventList.size();
    }

    // This is your ViewHolder class that helps to populate data to the view
    class ArtistesEventViewHolder extends RecyclerView.ViewHolder {

        public TextView tvArtistesNameOnEvent;

        public ArtistesEventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArtistesNameOnEvent = (TextView) itemView.findViewById(R.id.tvArtistesNameOnEvent);
        }
    }
}