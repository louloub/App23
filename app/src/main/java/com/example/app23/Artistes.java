package com.example.app23;

public class Artistes {

    private int id;
    private String title;
    private String shortdesc;
    private String image;

    public Artistes(int id, String title, String shortdesc, String image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getImage() {
        return image;
    }
}