package com.example.app23.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Artistes extends ArrayList<Artistes> implements Serializable {

    private String name;
    private String bio;
    private String photoUrl;
    private String facebook_url;
    private String soundcloudUrl;
    private String beatportUrl;
    private String mixcloudUrl;
    private String twitterUrl;
    private String residentAdvisorUrl;
    private String instagramUrl;
    private String siteUrl;

    public Artistes(String name, String bio, String photoUrl, String facebook_url, String soundcloudUrl,
                    String beatportUrl, String mixcloudUrl, String twitterUrl, String residentAdvisorUrl,
                    String instagramUrl, String siteUrl)
    {
        this.name = name;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.facebook_url = facebook_url;
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
        return facebook_url;
    }

    public void setFacebookUrl(String facebook_url) {
        this.facebook_url = facebook_url;
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

    @Override
    public Stream<Artistes> stream() {
        return null;
    }

    @Override
    public Stream<Artistes> parallelStream() {
        return null;
    }
}