package com.breja.breja_br.Models;

public class PromocoesDenunciadas {
   String email;
    String uriImg;
    String descrição;
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

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }
    public PromocoesDenunciadas(String email, String uriImg, String descrição) {
        this.email = email;
        this.uriImg = uriImg;
        this.descrição = descrição;
    }



}
