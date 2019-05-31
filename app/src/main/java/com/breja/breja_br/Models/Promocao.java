package com.breja.breja_br.Models;

import com.google.android.gms.maps.model.LatLng;

public class Promocao {
    private String id;
    private String beer;
    private String type_beer;
    private String content;
    private String description;
    private String estabelecimento;
    private LatLng latLng;
    private double value;
    private String UriImg;
    private String email;
    private int denunciado;
    public Promocao(){

    }
    public Promocao(String beer, String type_beer, String content, String description, String estabelecimento, LatLng latLng, double value, String uriImg, String email, int denunciado) {
        this.beer = beer;
        this.type_beer = type_beer;
        this.content = content;
        this.description = description;
        this.estabelecimento = estabelecimento;
        this.latLng = latLng;
        this.value = value;
        UriImg = uriImg;
        this.email = email;
        this.denunciado = denunciado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeer() {
        return beer;
    }

    public void setBeer(String beer) {
        this.beer = beer;
    }

    public String getType_beer() {
        return type_beer;
    }

    public void setType_beer(String type_beer) {
        this.type_beer = type_beer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUriImg() {
        return UriImg;
    }

    public void setUriImg(String uriImg) {
        UriImg = uriImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDenunciar() {
        return denunciado;
    }

    public void setDenunciar(int denunciado) {
        this.denunciado = denunciado;
    }


}
