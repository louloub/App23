package com.example.app23.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app23.Object.Event;
import com.example.app23.R;

public class EventPageActivity extends OptionMenuActivity {

    private static final String NAME_FOR_ACTIONBAR = "Jeu Concours YourDJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

        // Retrieve intent data
        Event event = (Event) getIntent().getSerializableExtra("event");

        String name = event.getName();
        String concoursUrl = event.getConcoursUrl();
        String photoUrl = event.getPhotoUrl();

        // XML
        TextView tvNameEvent = findViewById(R.id.tvNameEvent);
        Button buttonSharingFacebook = findViewById(R.id.btnConcours);
        ImageView ivPhotoEvent = findViewById(R.id.ivPhotoEvent);

        // SET EVENT IMAGE
        Glide.with(this)
                .load(event.getPhotoUrl())
                .into(ivPhotoEvent);

        // SET CONTENTS
        tvNameEvent.setText(name);

        // SET ON CLICK SHARING BUTTON
        buttonSharingFacebook.setOnClickListener(v ->
        {
            getOpenFacebookIntent(this.getPackageManager(), concoursUrl);
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getConcoursFacebookURL(this, concoursUrl);
            facebookIntent.setData(Uri.parse(facebookUrl));
            this.startActivity(facebookIntent);
        });
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
                uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/sharer/sharer.php?u=" + url);
            }

        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    // method to get the right URL to use in the intent
    public String getConcoursFacebookURL(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // newer versions of fb app
                return "fb://facewebmodal/f?href=https://www.facebook.com/sharer/sharer.php?u=" + url;
            } else { // older versions of fb app
                return "fb://page/f?href=https://www.facebook.com/sharer/sharer.php?u=" + url;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return url; // normal web url
        }
    }
}