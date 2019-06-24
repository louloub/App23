package com.example.app23.Object;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    private String photoUrl;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String facebookUrl;
    private Preventes preventes;
    private Artistes artistes;
    private Lieux lieux;

    public Event(String photoUrl, String name, Date dateStart, Date dateEnd, String facebookUrl,
                 Preventes preventes, Artistes artistes, Lieux lieux) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.facebookUrl = facebookUrl;
        this.preventes = preventes;
        this.artistes = artistes;
        this.lieux = lieux;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    public Date getDateStart() {
        return dateStart;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Preventes getPreventes() {
        return preventes;
    }

    public void setPreventes(Preventes preventes) {
        this.preventes = preventes;
    }

    public Artistes getArtistes() {
        return artistes;
    }

    public void setArtistes(Artistes artistes) {
        this.artistes = artistes;
    }

    public Lieux getLieux() {
        return lieux;
    }

    public void setLieux(Lieux lieux) {
        this.lieux = lieux;
    }
}
