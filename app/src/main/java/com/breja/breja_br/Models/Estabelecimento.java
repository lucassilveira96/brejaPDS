package com.breja.breja_br.Models;




public class Estabelecimento {
    private double lat;
    private double lng;
    private String estabelecimento;
    public Estabelecimento(double lat,double lng, String estabelecimento) {
        this.lat=lat;
        this.lng=lng;
        this.estabelecimento = estabelecimento;
    }
    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
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


}
