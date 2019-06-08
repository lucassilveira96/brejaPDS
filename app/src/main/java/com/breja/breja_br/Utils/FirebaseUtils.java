package com.breja.breja_br.Utils;

import android.location.Location;

import com.breja.breja_br.Models.Promocao;

public class FirebaseUtils {
    public static double distanceFromDatabasePlace(double lat, double lng, Promocao promocaotFromDatabase) {
        if(promocaotFromDatabase == null) {
            return -1;
        }

        Location startPoint = new Location("startPoint");
        startPoint.setLatitude(lat);
        startPoint.setLongitude(lng);

        Location endPoint = new Location("endPoint");
        endPoint.setLatitude(promocaotFromDatabase.getLat());
        endPoint.setLongitude(promocaotFromDatabase.getLng());

        double distance = startPoint.distanceTo(endPoint) * 0.001d;
        return distance;
    }
}
