package com.example.app23;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;
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

        holder.tvName.setText(artistes.getName());
        holder.tvBio.setText(artistes.getBio());

        Glide.with(mCtx)
                .load(artistes.getPhotoUrl())
                .into(holder.ivPhoto);

        holder.ivFacebook.setOnClickListener(v -> {
            String facebook = artistes.getFacebook_url();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebook));
            mCtx.startActivity(intent);
        });

        holder.ivSoundcloud.setOnClickListener(v -> {
            String soundcloud = artistes.getSoundcloudUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(soundcloud));
            mCtx.startActivity(intent);
        });

        holder.ivBeatport.setOnClickListener(v -> {
            String beatport = artistes.getBeatportUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(beatport));
            mCtx.startActivity(intent);
        });

        holder.ivMixcloud.setOnClickListener(v -> {
            String mixcloudUrl = artistes.getMixcloudUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mixcloudUrl));
            mCtx.startActivity(intent);
        });

        holder.ivTwitter.setOnClickListener(v -> {
            String twitter = artistes.getTwitterUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(twitter));
            mCtx.startActivity(intent);
        });

        holder.ivResidentAdvisor.setOnClickListener(v -> {
            String residentAdvisor = artistes.getResidentAdvisorUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(residentAdvisor));
            mCtx.startActivity(intent);
        });

        holder.ivSite.setOnClickListener(v -> {
            String site = artistes.getSiteUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(site));
            mCtx.startActivity(intent);
        });

        //-------------------------------------------------
        // LISTENER FOR WHEN WE CLICK ON ARTIST IN THE LIST
        //-------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

                String name = artistes.getName();
                String bio = artistes.getBio();
                String photo = artistes.getPhotoUrl();
                String facebook = artistes.getFacebook_url();
                String soundcloud = artistes.getSoundcloudUrl();
                String beatport = artistes.getBeatportUrl();
                String mixcloud = artistes.getMixcloudUrl();
                String twitter = artistes.getTwitterUrl();
                String residentAdvisor = artistes.getResidentAdvisorUrl();
                String instagram = artistes.getInstagramUrl();
                String site = artistes.getSiteUrl();

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

        TextView tvName, tvBio;
        ImageView ivPhoto, ivFacebook, ivSoundcloud, ivBeatport, ivMixcloud, ivTwitter, ivResidentAdvisor, ivInstagram, ivSite;

        public ArtistesViewHolder(View itemView) {
            super(itemView);

            // TODO : check si ca marche :
            /*ImageRequest imageRequest = new ImageRequest(ivPhoto, (Response.Listener<Bitmap>) responseImageRequest ->
                    ivPhoto.setImageBitmap(responseImageRequest));*/

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

            if (ivFacebook == null) {
                ivFacebook.setVisibility(GONE);
            }

            if (ivSoundcloud == null) {
                ivSoundcloud.setVisibility(GONE);
            }

            if (ivBeatport == null) {
                ivBeatport.setVisibility(GONE);
            }

            if (ivMixcloud == null) {
                ivMixcloud.setVisibility(GONE);
            }

            if (ivTwitter == null) {
                ivTwitter.setVisibility(GONE);
            }

            if (ivResidentAdvisor == null) {
                ivResidentAdvisor.setVisibility(GONE);
            }

            if (ivInstagram == null) {
                ivInstagram.setVisibility(GONE);
            }

            if (ivSite == null) {
                ivSite.setVisibility(GONE);
            }


            ivSoundcloud.setVisibility(GONE);
            ivBeatport.setVisibility(GONE);
            ivMixcloud.setVisibility(GONE);
            ivTwitter.setVisibility(GONE);
            ivResidentAdvisor.setVisibility(GONE);
            ivInstagram.setVisibility(GONE);
            ivSite.setVisibility(GONE);


        }
    }
}