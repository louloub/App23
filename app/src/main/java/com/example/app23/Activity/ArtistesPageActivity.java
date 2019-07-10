package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app23.Object.Artistes;
import com.example.app23.R;

import static android.view.View.GONE;

public class ArtistesPageActivity extends OptionMenuActivity {

    private static final String NAME_FOR_ACTIONBAR = "Page Artiste";
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

        // BUTTON ANIMATION
        Animation animation = AnimationUtils.loadAnimation(ArtistesPageActivity.this, R.anim.alpha);

        tv_firstname.setText(name);
        tv_lastName.setText(bio);

        iv_facebook.setOnClickListener(v -> {
            iv_facebook.startAnimation(animation);
            String facebookUrl = artiste.getFacebookUrl();

            getOpenFacebookIntent(this.getPackageManager(), facebookUrl);
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrlApp = getFacebookURL(this, facebookUrl);
            facebookIntent.setData(Uri.parse(facebookUrlApp));
            this.startActivity(facebookIntent);
        });

        iv_soundcloud.setOnClickListener(v -> {
            iv_soundcloud.startAnimation(animation);
            String soundcloudUrl = artiste.getSoundcloudUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(soundcloudUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_beatport.setOnClickListener(v -> {
            iv_beatport.startAnimation(animation);
            String beatportUrl = artiste.getBeatportUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(beatportUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_mixcloud.setOnClickListener(v -> {
            iv_mixcloud.startAnimation(animation);
            String mixcloudUrl = artiste.getMixcloudUrl();
            Intent intent = null;

            try {
                // get the Mixcloud app if possible
                this.getPackageManager().getPackageInfo("com.mixcloud.player", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mixcloudUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Mixcloud app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mixcloudUrl));
            }
            this.startActivity(intent);
        });

        iv_twitter.setOnClickListener(v -> {
            iv_twitter.startAnimation(animation);
            String twitterUrl = artiste.getTwitterUrl();
            Intent intent = null;
            try {
                // get the Twitter app if possible
                this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
            }
            this.startActivity(intent);
        });

        iv_residentAdvisor.setOnClickListener(v -> {
            iv_residentAdvisor.startAnimation(animation);
            String residentAdvisorUrl = artiste.getResidentAdvisorUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(residentAdvisorUrl));
            getApplicationContext().startActivity(intent);
        });

        iv_instagram.setOnClickListener(v -> {
            iv_instagram.startAnimation(animation);
            String instagramUrl = artiste.getInstagramUrl();
            Intent intent = null;
            try {
                // get the Instagram app if possible
                this.getPackageManager().getPackageInfo("com.instagram.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Instagram app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
            }
            this.startActivity(intent);
        });

        iv_site.setOnClickListener(v -> {
            iv_site.startAnimation(animation);
            String siteUrl = artiste.getSiteUrl();
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.setData(Uri.parse(siteUrl));
            getApplicationContext().startActivity(intent);
        });

        // Get Photo from URL in artiste objet
        Glide.with(getApplicationContext())
                .load(photo)
                .into(iv_photo);

        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);
    }

    //----------------------
    // INTENT FB APPLICATION
    //----------------------
    public static Intent getOpenFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    // method to get the right URL to use in the intent
    public static String getFacebookURL(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // newer versions of fb app
                return "fb://facewebmodal/f?href=" + url + "posts";
            } else { // older versions of fb app
                return "fb://page/" + url;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return url; // normal web url
        }
    }
}