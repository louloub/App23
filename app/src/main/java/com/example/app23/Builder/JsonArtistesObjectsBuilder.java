package com.example.app23.Builder;

import com.example.app23.Object.Artistes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonArtistesObjectsBuilder {

    public static Artistes getArtistesObjectsFromAPI (JSONArray jsonArrayArtistesAPI, int i)
    {
        JSONObject jsonArtistesObjects = null;
        try {
            jsonArtistesObjects = jsonArrayArtistesAPI.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String name = "";
        String bio = "";
        String photoUrl = "";
        String facebook_url = "";
        String soundcloudUrl = "";
        String beatportUrl = "";
        String mixcloudUrl = "";
        String twitterUrl = "";
        String residentAdvisorUrl = "";
        String instagramUrl = "";
        String siteUrl = "";

        Artistes artistesFromApi = new Artistes(name,bio,photoUrl,facebook_url,
                soundcloudUrl,beatportUrl,mixcloudUrl,twitterUrl,residentAdvisorUrl,
                instagramUrl,siteUrl);

        // NAME
        if (jsonArtistesObjects.has("name"))
        {
            if (!jsonArtistesObjects.isNull("name"))
            {
                String newName = null;
                try {
                    newName = jsonArtistesObjects.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setName(newName);
            } else {
            }
        } else {
        }

        // BIO
        if (jsonArtistesObjects.has("content"))
        {
            if (!jsonArtistesObjects.isNull("content"))
            {
                String newBio = null;
                try {
                    newBio = jsonArtistesObjects.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setBio(newBio);
            } else {
            }
        } else {
        }

        // PHOTO
        if (jsonArtistesObjects.has("image"))
        {
            if (!jsonArtistesObjects.isNull("image"))
            {
                String newPhotoUrl = null;
                try {
                    newPhotoUrl = jsonArtistesObjects.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setPhotoUrl("https://www.yourdj.fr/" +newPhotoUrl);
            } else {
            }
        } else {
        }

        // FB
        if (jsonArtistesObjects.has("urlfb"))
        {
            if (!jsonArtistesObjects.isNull("urlfb"))
            {
                String newFacebook = null;
                try {
                    newFacebook = jsonArtistesObjects.getString("urlfb");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setFacebookUrl(newFacebook);
            } else {
            }
        } else {
        }

        // SC
        if (jsonArtistesObjects.has("urlsc"))
        {
            if (!jsonArtistesObjects.isNull("urlsc"))
            {
                String newSoundcloud = null;
                try {
                    newSoundcloud = jsonArtistesObjects.getString("urlsc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setSoundcloudUrl(newSoundcloud);
            } else {
            }
        } else {
        }

        // BP
        if (jsonArtistesObjects.has("urlbp"))
        {
            if (!jsonArtistesObjects.isNull("urlbp"))
            {
                String newBeatport = null;
                try {
                    newBeatport = jsonArtistesObjects.getString("urlbp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setBeatportUrl(newBeatport);
            } else {
            }
        } else {
        }

        // MC
        if (jsonArtistesObjects.has("urlmc"))
        {
            if (!jsonArtistesObjects.isNull("urlmc"))
            {
                String newMixcloud = null;
                try {
                    newMixcloud = jsonArtistesObjects.getString("urlmc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setMixcloudUrl(newMixcloud);
            } else {
            }
        } else {
        }

        // TW
        if (jsonArtistesObjects.has("urltw"))
        {
            if (!jsonArtistesObjects.isNull("urltw"))
            {
                String newTwitter = null;
                try {
                    newTwitter = jsonArtistesObjects.getString("urltw");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setTwitterUrl(newTwitter);
            } else {
            }
        } else {
        }

        // RA
        if (jsonArtistesObjects.has("urlra"))
        {
            if (!jsonArtistesObjects.isNull("urlra"))
            {
                String newResidentAdvisor = null;
                try {
                    newResidentAdvisor = jsonArtistesObjects.getString("urlra");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setResidentAdvisorUrl(newResidentAdvisor);
            } else {
            }
        } else {
        }

        // INSTA
        if (jsonArtistesObjects.has("urlinsta"))
        {
            if (!jsonArtistesObjects.isNull("urlinsta"))
            {
                String newInstagram = null;
                try {
                    newInstagram = jsonArtistesObjects.getString("urlinsta");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setInstagramUrl(newInstagram);
            } else {
            }
        } else {
        }

        // SITE
        if (jsonArtistesObjects.has("urlsite"))
        {
            if (!jsonArtistesObjects.isNull("urlsite"))
            {
                String newSite = null;
                try {
                    newSite = jsonArtistesObjects.getString("urlsite");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                artistesFromApi.setSiteUrl(newSite);
            } else {
            }
        } else {
        }
        return artistesFromApi;
    }
}