package com.example.app23.Object;

import java.io.Serializable;

public class Lieux implements Serializable {

    private String photoUrl;
    private String name;
    private String adresse;
    private String mapLieuIframe;
    private String facebookUrl;
    private String siteUrl;

    public Lieux(String photoUrl, String name, String adresse, String mapLieuIframe,
                 String facebookUrl, String siteUrl) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.adresse = adresse;
        this.mapLieuIframe = mapLieuIframe;
        this.facebookUrl = facebookUrl;
        this.siteUrl = siteUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMapLieuIframe() {
        return mapLieuIframe;
    }

    public void setMapLieuIframe(String mapLieuIframe) {
        this.mapLieuIframe = mapLieuIframe;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
