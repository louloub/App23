package com.example.app23.Object;

import java.io.Serializable;

public class Preventes implements Serializable {

    private int prix;
    private int nombre;

    public Preventes(int prix, int nombre) {
        this.prix = prix;
        this.nombre = nombre;
    }

    public int getPrix(int prix) {
        return this.prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNombre(int nombre) {
        return this.nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
