package com.breja.breja_br.Utils;

public class localizacao {
    private double latPoint;
    private double lngPoint;

    public localizacao() {
    }
    public localizacao(double latPoint, double lngPoint) {
        this.latPoint = latPoint;
        this.lngPoint = lngPoint;
    }
    public double getLatPoint() {
        return latPoint;
    }

    public void setLatPoint(double latPoint) {
        this.latPoint = latPoint;
    }

    public double getLngPoint() {
        return lngPoint;
    }

    public void setLngPoint(double lngPoint) {
        this.lngPoint = lngPoint;
    }






}