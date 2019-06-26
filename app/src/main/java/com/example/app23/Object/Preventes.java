package com.example.app23.Object;

import java.io.Serializable;

public class Preventes implements Serializable {

    private int prix;
    private int nombre;
    private String preventesUrl;

    public Preventes(int prix, int nombre, String preventesUrl) {
        this.prix = prix;
        this.nombre = nombre;
        this.preventesUrl = preventesUrl;
    }

    public String getPreventesUrl() {
        return preventesUrl;
    }

    public void setPreventesUrl(String preventesUrl) {
        this.preventesUrl = preventesUrl;
    }

    public int getPrix() {
        return this.prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNombre() {
        return this.nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
