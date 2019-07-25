package com.example.app23.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app23.Object.Artistes;
import com.example.app23.Activity.ArtistesPageActivity;
import com.example.app23.R;

import java.util.List;

import static android.view.View.GONE;

public class ArtistesAdapter extends RecyclerView.Adapter<ArtistesAdapter.ArtistesViewHolder>  {

    private Context mCtx;
    private List<Artistes> artistesList;

    public ArtistesAdapter(Context mCtx, List<Artistes> artistesList) {
        this.mCtx = mCtx;
        this.artistesList = artistesList;
    }

    @Override
    public ArtistesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.artistes_list_model, null);
        return new ArtistesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistesViewHolder holder, int position) {
        Artistes artistes = artistesList.get(position);

        String name = artistes.getName();
        String bio = artistes.getBio();
        String photo = artistes.getPhotoUrl();
        String facebook = artistes.getFacebookUrl();
        String soundcloud = artistes.getSoundcloudUrl();
        String beatport = artistes.getBeatportUrl();
        String mixcloud = artistes.getMixcloudUrl();
        String twitter = artistes.getTwitterUrl();
        String residentAdvisor = artistes.getResidentAdvisorUrl();
        String site = artistes.getSiteUrl();
        String instagram = artistes.getInstagramUrl();

        holder.tvName.setText(artistes.getName());
        holder.tvBio.setText(artistes.getBio());

        // LIMITER LA TAILLE DU TEXT PAR ITEM ET AFFICHER LES POINTS DE SUPSENSION
        holder.tvBio.setMaxHeight(200);
        holder.tvBio.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvBio.setLines(5);
        holder.tvBio.setMaxLines(5);

        // RECUPERATION DE LA PHOTO AVEC SON URL
        Glide.with(mCtx)
                .load(artistes.getPhotoUrl())
                .into(holder.ivPhoto);

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON ARTIST IN THE LIST
        //-------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

                Artistes artiste = new Artistes(name,bio,photo,facebook,soundcloud,beatport,
                        mixcloud,twitter,residentAdvisor,instagram,site);

                Intent intent = new Intent(mCtx, ArtistesPageActivity.class);
                intent.putExtra("artiste", artiste);

                mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return artistesList.size();
    }

    class ArtistesViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvBio;
        ImageView ivPhoto, ivFacebook, ivSoundcloud, ivBeatport, ivMixcloud, ivTwitter, ivResidentAdvisor, ivInstagram, ivSite;

        public ArtistesViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvBio = itemView.findViewById(R.id.tvBio);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivFacebook = itemView.findViewById(R.id.ivFacebook);
            ivSoundcloud = itemView.findViewById(R.id.ivSoundcloud);
            ivBeatport = itemView.findViewById(R.id.ivBeatport);
            ivMixcloud = itemView.findViewById(R.id.ivMixcloud);
            ivTwitter = itemView.findViewById(R.id.ivTwitter);
            ivResidentAdvisor = itemView.findViewById(R.id.ivResidentAdvisor);
            ivInstagram = itemView.findViewById(R.id.ivInstagram);
            ivSite = itemView.findViewById(R.id.ivSite);

        }
    }
}