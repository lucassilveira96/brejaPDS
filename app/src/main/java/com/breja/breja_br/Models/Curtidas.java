package com.breja.breja_br.Models;

public class Curtidas {
 private String email;
    private String uriImg;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUriImg() {
        return uriImg;
    }

    public void setUriImg(String uriImg) {
        this.uriImg = uriImg;
    }
    public Curtidas(String email, String uriImg) {
        this.email = email;
        this.uriImg = uriImg;
    }


}
