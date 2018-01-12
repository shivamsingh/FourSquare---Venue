package com.foursquare.model.venue.remote;

public class Location {
  private int distance;
  private String[] formattedAddress;
  private double lat;
  private double lng;

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public void setFormattedAddress(String[] formattedAddress) {
    this.formattedAddress = formattedAddress;
  }

  int getDistance() {
    return distance;
  }

  String formattedAddress() {
    StringBuilder addressBuilder = new StringBuilder();
    for (String address : formattedAddress)
      addressBuilder.append(address).append('\n');
    return addressBuilder.toString();
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