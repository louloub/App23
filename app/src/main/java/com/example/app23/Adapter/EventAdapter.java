package com.example.app23.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Date;
import java.util.List;

import static android.view.View.GONE;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>  {

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

        // Nom, date et DJ obligatoirement rempli à la création d'un événement sur le site

        String photo = event.getPhotoUrl();
        String name = event.getName();
        Date dateStart = event.getDateStart();
        Date dateEnd = event.getDateEnd();
        String facebook = event.getFacebookUrl();

        // PREVENTES
        /*Preventes preventes = event.getPreventes();
        int prixPreventes = preventes.getPrix(object.getInt("prix"));
        int nbrPreventes = preventes.getNombre(object.getInt("nombre"));*/

        // ARTISTES
        Artistes artistes = event.getArtistes();
        String artisteName = artistes.getName();
        // TODO : rendre le DJ cliquable si il est sur notre site

        // LIEUX
        Lieux lieux = event.getLieux();
        String nomLieux = lieux.getName();

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



        // TODO : masquer ou non si les éléments sont vides ou non
        // TODO trouve une solution pour remplacer GLIDE

        /*// RECUPERATION DE LA PHOTO AVEC SON URL
        Glide.with(mCtx)
                .load(event.getPhotoUrl())
                .into(holder.ivPhotoEvent);*/

        holder.tvNameEvent.setText(event.getName());
        holder.tvDateStart.setText((CharSequence) dateStart);
        holder.tvDateEnd.setText((CharSequence) dateEnd);
        holder.tvArtisteName.setText(artisteName);
        // holder.tvNbrPreventes.setText(prixPreventes);
        // holder.tvNbrPreventes.setText(nbrPreventes);
        holder.tvLieux.setText(nomLieux);

        //--------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON SOCIAL NETWORK ICON
        //--------------------------------------------------
        holder.ivFacebookEvent.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebook));
            mCtx.startActivity(intent);
        });

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON ARTIST IN THE LIST
        //-------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

            // Event eventForIntent = new Event(photo,name,dateStart,dateEnd,facebook,preventes,artistes,lieux);

            Intent intent = new Intent(mCtx, EventPageActivity.class);
            // intent.putExtra("event", eventForIntent);

            mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameEvent, tvDateStart, tvDateEnd, tvArtisteName, tvLieux, tvNbrPreventes, tvPrixPreventes;
        ImageView ivPhotoEvent, ivFacebookEvent;

        public EventViewHolder(View itemView) {
            super(itemView);

            ivPhotoEvent = itemView.findViewById(R.id.ivPhotoEvent);
            tvNameEvent = itemView.findViewById(R.id.tvNameEvent);
            tvDateStart = itemView.findViewById(R.id.tvDateStart);
            tvDateEnd = itemView.findViewById(R.id.tvDateEnd);
            ivFacebookEvent = itemView.findViewById(R.id.ivFacebookEvent);
            // tvNbrPreventes = itemView.findViewById(R.tvNbrPreventes);
            // tvPrixPreventes = itemView.findViewById(R.tvPrixPreventes);
            tvArtisteName = itemView.findViewById(R.id.tvArtisteName);
            tvLieux = itemView.findViewById(R.id.tvLieux);
        }
    }
}