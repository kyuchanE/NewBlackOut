package com.androidtown.blackout;

public class ItemList {
    String date;
    String lat;
    String lng;

    public ItemList(String date, String lat, String lng) {
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
