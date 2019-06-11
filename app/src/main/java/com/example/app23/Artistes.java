package com.example.app23;

import java.io.Serializable;

public class Artistes implements Serializable {

    private String firstname;
    private String lastname;
    private String age;

    public Artistes(String firstname, String lastname, String age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAge() {
        return age;
    }
}