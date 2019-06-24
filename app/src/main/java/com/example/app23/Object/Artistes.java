package com.example.app23.Object;

import java.io.Serializable;

public class Artistes implements Serializable {

    private String name;
    private String bio;
    private String photoUrl;
    private String facebookUrl;
    private String soundcloudUrl;
    private String beatportUrl;
    private String mixcloudUrl;
    private String twitterUrl;
    private String residentAdvisorUrl;
    private String instagramUrl;
    private String siteUrl;

    public Artistes(String name, String bio, String photoUrl, String facebookUrl, String soundcloudUrl,
                    String beatportUrl, String mixcloudUrl, String twitterUrl, String residentAdvisorUrl,
                    String instagramUrl, String siteUrl)
    {
        this.name = name;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.facebookUrl = facebookUrl;
        this.soundcloudUrl = soundcloudUrl;
        this.beatportUrl = beatportUrl;
        this.mixcloudUrl = mixcloudUrl;
        this.twitterUrl = twitterUrl;
        this.residentAdvisorUrl = residentAdvisorUrl;
        this.instagramUrl = instagramUrl;
        this.siteUrl = siteUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    public void setSoundcloudUrl(String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }

    public String getBeatportUrl() {
        return beatportUrl;
    }

    public void setBeatportUrl(String beatportUrl) {
        this.beatportUrl = beatportUrl;
    }

    public String getMixcloudUrl() {
        return mixcloudUrl;
    }

    public void setMixcloudUrl(String mixcloudUrl) {
        this.mixcloudUrl = mixcloudUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getResidentAdvisorUrl() {
        return residentAdvisorUrl;
    }

    public void setResidentAdvisorUrl(String residentAdvisorUrl) {
        this.residentAdvisorUrl = residentAdvisorUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}