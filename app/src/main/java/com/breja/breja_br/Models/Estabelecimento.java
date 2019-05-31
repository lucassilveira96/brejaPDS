package com.breja.breja_br.Models;


import com.google.android.gms.maps.model.LatLng;

public class Estabelecimento {
    private LatLng latLng;
    private String estabelecimento;
    public Estabelecimento(LatLng latLng, String estabelecimento) {
        this.latLng = latLng;
        this.estabelecimento = estabelecimento;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }


}
