package com.buffet_user.pojo;

/**
 * Created by Ankit on 13/08/17.
 */

public class VenueItems {

    private double latitude;
    private double longitude;
    private String Address = "";

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
