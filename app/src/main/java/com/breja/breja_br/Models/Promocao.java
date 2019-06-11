package com.breja.breja_br.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Promocao{
    private String id;
    private String beer;
    private String type_beer;
    private String content;
    private String description;
    private String estabelecimento;
    private double lat;
    private double lng;
    private double value;
    private String UriImg;
    private String email;
    private String validade;
    private int denunciado;
    public Promocao(){

    }
    public Promocao(String beer, String type_beer, String content, String description, String estabelecimento, double lat, double lng, double value, String uriImg, String email, String validade, int denunciado) {
        this.beer = beer;
        this.type_beer = type_beer;
        this.content = content;
        this.description = description;
        this.estabelecimento = estabelecimento;
        this.lat = lat;
        this.lng = lng;
        this.value = value;
        UriImg = uriImg;
        this.email = email;
        this.denunciado = denunciado;
        this.validade = validade;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public int getDenunciado() {
        return denunciado;
    }

    public void setDenunciado(int denunciado) {
        this.denunciado = denunciado;
    }



}
