package com.example.app23.Builder;

import android.util.Log;

import static com.android.volley.VolleyLog.TAG;

public class UrlBuilder {

    public static String getUrl(String city, int page, int contentperpages) {
        String uri = "https://www.yourdj.fr/api/1.0/dj/?city=" +city+ "&page=" +page+ "&contentperpages=" +contentperpages+ "";
        Log.d(TAG,"loadArtistesM uri " +uri );
        return uri;
    }
}
