package com.example.app23;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.view.View.GONE;

public class ArtistesPage extends OptionMenuActivity {

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_page);

        // Retrieve intent data
        Artistes artiste = (Artistes) getIntent().getSerializableExtra("artiste");

        // Retrieve data's objet
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
        String site = artiste.getSiteUrl();

        TextView tv_firstname = findViewById(R.id.tvName);
        TextView tv_lastName = findViewById(R.id.tvBio);
        ImageView iv_photo = findViewById(R.id.ivPhoto);
        ImageView iv_facebook = findViewById(R.id.ivFacebook);
        ImageView iv_soundcloud = findViewById(R.id.ivSoundcloud);
        ImageView iv_beatport = findViewById(R.id.ivBeatport);
        ImageView iv_mixcloud = findViewById(R.id.ivMixcloud);
        ImageView iv_twitter = findViewById(R.id.ivTwitter);
        ImageView iv_residentAdvisor = findViewById(R.id.ivResidentAdvisor);
        ImageView iv_instagram = findViewById(R.id.ivInstagram);
        ImageView iv_site = findViewById(R.id.ivSite);

        //------------------------------------------------
        // DISPLAY SOCIAL NETWORK ICON IF THEIR ARE AN URL
        //------------------------------------------------

        if (facebook.isEmpty()) {
            iv_facebook.setVisibility(GONE);
        }

        if (soundcloud.isEmpty()) {
            iv_soundcloud.setVisibility(GONE);
        }

        if (beatport.isEmpty()) {
            iv_beatport.setVisibility(GONE);
        }

        if (mixcloud.isEmpty()) {
            iv_mixcloud.setVisibility(GONE);
        }

        if (twitter.isEmpty()) {
            iv_twitter.setVisibility(GONE);
        }

        if (residentAdvisor.isEmpty()) {
            iv_residentAdvisor.setVisibility(GONE);
        }

        if (instagram.isEmpty()) {
            iv_instagram.setVisibility(GONE);
        }

        if (site.isEmpty()) {
            iv_site.setVisibility(GONE);
        }

        //---------------------------------------------------------------------------
        // Set content with retrieve data about intent + Click on social network icon
        //---------------------------------------------------------------------------

        tv_firstname.setText(name);
        tv_lastName.setText(bio);

        iv_facebook.setOnClickListener(v -> {
            String facebookUrl = artiste.getFacebookUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(facebookUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_soundcloud.setOnClickListener(v -> {
            String soundcloudUrl = artiste.getSoundcloudUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(soundcloudUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_beatport.setOnClickListener(v -> {
            String beatportUrl = artiste.getBeatportUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(beatportUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_mixcloud.setOnClickListener(v -> {
            String mixcloudUrl = artiste.getMixcloudUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mixcloudUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_twitter.setOnClickListener(v -> {
            String twitterUrl = artiste.getTwitterUrl();
            if (!twitterUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(twitterUrl));
                getApplicationContext().startActivity(intent);
            }
            else {

            }
        });

        iv_residentAdvisor.setOnClickListener(v -> {
            String residentAdvisorUrl = artiste.getResidentAdvisorUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(residentAdvisorUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_instagram.setOnClickListener(v -> {
            String instagramUrl = artiste.getInstagramUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(instagramUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_site.setOnClickListener(v -> {
            String siteUrl = artiste.getSiteUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(siteUrl));
            getApplicationContext().startActivity(intent);
        });

        // Get Photo from URL in artiste objet
        Glide.with(getApplicationContext())
                .load(photo)
                .into(iv_photo);

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
