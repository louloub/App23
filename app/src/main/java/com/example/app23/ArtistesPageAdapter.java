package com.example.app23;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class ArtistesPageAdapter extends RecyclerView.Adapter<ArtistesPageAdapter.ArtistesPageViewHolder>  {

    private Context mCtx;
    private List<Artistes> artistesList;

    public ArtistesPageAdapter(Context mCtx, List<Artistes> artistesList) {
        this.mCtx = mCtx;
        this.artistesList = artistesList;
    }

    @Override
    public ArtistesPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.artistes_list, null);
        return new ArtistesPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistesPageViewHolder holder, int position) {
        Artistes artistes = artistesList.get(position);

        //loading the image
        /*Glide.with(mCtx)
                .load(artistes.getImage())
                .into(holder.imageView);*/

        holder.textViewTitle.setText(artistes.getFirstname());
        holder.textViewShortDesc.setText(artistes.getLastname());
        holder.age.setText(artistes.getAge());
        holder.itemView.setOnClickListener(v -> {


            String firstName = artistes.getFirstname();
            String lastName = artistes.getLastname();
            String age = artistes.getAge();

            Artistes artiste = new Artistes(firstName,lastName,age);

            Intent intent = new Intent(mCtx, ArtistesPage.class);

            intent.putExtra("artiste", artiste);

            mCtx.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return artistesList.size();
    }

    class ArtistesPageViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, age;
        ImageView imageView;

        public ArtistesPageViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            age = itemView.findViewById(R.id.age);
        }
    }
}