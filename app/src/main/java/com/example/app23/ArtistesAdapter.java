package com.example.app23;

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

import java.util.List;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
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

        //------------------------------------
        // DISPLAY ICON SOCIAL NETWORK IF LINK
        //------------------------------------
            if (facebook.isEmpty()) {
                holder.ivFacebook.setVisibility(GONE);
            }
            else {}

            if (soundcloud.isEmpty()) {
                holder.ivSoundcloud.setVisibility(GONE);
            }
            else {}

            if (beatport.isEmpty()) {
                holder.ivBeatport.setVisibility(GONE);
            }
            else {}

            if (mixcloud.isEmpty()) {
                holder.ivMixcloud.setVisibility(GONE);
            }
            else {}

            if (twitter.isEmpty()) {
                holder.ivTwitter.setVisibility(GONE);
            }
            else {}

            if (residentAdvisor.isEmpty()) {
                holder.ivResidentAdvisor.setVisibility(GONE);
            }
            else {}

            if (instagram.isEmpty()) {
                holder.ivInstagram.setVisibility(GONE);
            }
            else {}

            if (site.isEmpty()) {
                holder.ivSite.setVisibility(GONE);
            }
            else {}

        holder.tvName.setText(artistes.getName());
        holder.tvBio.setText(artistes.getBio());

        Glide.with(mCtx)
                .load(artistes.getPhotoUrl())
                .into(holder.ivPhoto);

        //--------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON SOCIAL NETWORK ICON
        //--------------------------------------------------
        holder.ivFacebook.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebook));
            mCtx.startActivity(intent);
        });

        holder.ivSoundcloud.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(soundcloud));
            mCtx.startActivity(intent);
        });

        holder.ivBeatport.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(beatport));
            mCtx.startActivity(intent);
        });

        holder.ivMixcloud.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mixcloud));
            mCtx.startActivity(intent);
        });

        holder.ivTwitter.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(twitter));
            mCtx.startActivity(intent);
        });

        holder.ivResidentAdvisor.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(residentAdvisor));
            mCtx.startActivity(intent);

        });

        holder.ivSite.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(site));
            mCtx.startActivity(intent);
        });

        holder.ivInstagram.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(instagram));
            mCtx.startActivity(intent);
        });

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON ARTIST IN THE LIST
        //-------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

                /*String name = artistes.getName();
                String bio = artistes.getBio();
                String photo = artistes.getPhotoUrl();
                String facebook = artistes.getFacebookUrl();
                String soundcloud = artistes.getSoundcloudUrl();
                String beatport = artistes.getBeatportUrl();
                String mixcloud = artistes.getMixcloudUrl();
                String twitter = artistes.getTwitterUrl();
                String residentAdvisor = artistes.getResidentAdvisorUrl();
                String instagram = artistes.getInstagramUrl();
                String site = artistes.getSiteUrl();*/

                Artistes artiste = new Artistes(name,bio,photo,facebook,soundcloud,beatport,
                        mixcloud,twitter,residentAdvisor,instagram,site);

                Intent intent = new Intent(mCtx, ArtistesPage.class);
                intent.putExtra("artiste", artiste);

                mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return artistesList.size();
    }

    class ArtistesViewHolder extends RecyclerView.ViewHolder {

        /*// Retrieve intent data
        Intent i = getIntent();
        Artistes artiste = (Artistes)i.getSerializableExtra("artiste");*/

        /*// Retrieve data's objet
        String name = artiste.getName();
        String bio = artiste.getBio();
        String photo = artiste.getPhotoUrl();
        String facebook = artiste.getFacebookUrl();
        String soundcloud = artiste.getSoundcloudUrl();
        String beatport = artiste.getBeatportUrl();
        String mixcloud = artiste.getMixcloudUrl();
        String twitter = artiste.getTwitterUrl();
        String residentAdvisor = artiste.getResidentAdvisorUrl();
        String instagram = artiste.getInstagramUrl();
        String site = artiste.getSiteUrl();*/

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

            /*if (facebook.isEmpty()) {
                ivFacebook.setVisibility(GONE);
            }

            if (soundcloud.isEmpty()) {
                ivSoundcloud.setVisibility(GONE);
            }

            if (beatport.isEmpty()) {
                ivBeatport.setVisibility(GONE);
            }

            if (mixcloud.isEmpty()) {
                ivMixcloud.setVisibility(GONE);
            }

            if (twitter.isEmpty()) {
                ivTwitter.setVisibility(GONE);
            }

            if (residentAdvisor.isEmpty()) {
                ivResidentAdvisor.setVisibility(GONE);
            }

            if (instagram.isEmpty()) {
                ivInstagram.setVisibility(GONE);
            }

            if (site.isEmpty()) {
                ivSite.setVisibility(GONE);
            }*/

            /*
            ivSoundcloud.setVisibility(GONE);
            ivBeatport.setVisibility(GONE);
            ivMixcloud.setVisibility(GONE);
            ivTwitter.setVisibility(GONE);
            ivResidentAdvisor.setVisibility(GONE);
            ivInstagram.setVisibility(GONE);
            ivSite.setVisibility(GONE);
            */


        }
    }
}