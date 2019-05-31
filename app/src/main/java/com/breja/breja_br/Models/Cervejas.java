package com.breja.breja_br.Models;

public class Cervejas {
    private String name_beer;
    private String alcohol_content;
    private String type_beer;
    private String user_email;
    private String Uid;
    private boolean released;

    public Cervejas(String name_beer, String alcohol_content, String user_email, String uid, String type_beer) {
        this.name_beer = name_beer;
        this.alcohol_content = alcohol_content;
        this.user_email = user_email;
        this.type_beer = type_beer;
        this.Uid = uid;
        this.released = true;
    }
    public String getName_beer() {
        return name_beer;
    }

    public void setName_beer(String name_beer) {
        this.name_beer = name_beer;
    }

    public String getAlcohol_content() {
        return alcohol_content;
    }

    public void setAlcohol_content(String alcohol_content) {
        this.alcohol_content = alcohol_content;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getType_beer() {
        return type_beer;
    }

    public void setType_beer(String type_beer) {
        this.type_beer = type_beer;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }


}