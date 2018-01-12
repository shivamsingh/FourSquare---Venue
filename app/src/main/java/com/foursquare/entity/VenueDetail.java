package com.foursquare.entity;

public class VenueDetail extends Venue {
  private String hoursStatus;
  private String formattedPhone;
  private String formattedAddress;
  private String bestPhoto;
  private double latitude;
  private double longitude;
  private String phone;

  public String getHoursStatus() {
    return hoursStatus;
  }

  public String getFormattedPhone() {
    return formattedPhone;
  }

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public void setFormattedAddress(String formattedAddress) {
    this.formattedAddress = formattedAddress;
  }

  public String getBestPhoto() {
    return bestPhoto;
  }

  public void setBestPhoto(String bestPhoto) {
    this.bestPhoto = bestPhoto;
  }

  public void setHoursStatus(String hoursStatus) {
    this.hoursStatus = hoursStatus;
  }

  public void setFormattedPhone(String formattedPhone) {
    this.formattedPhone = formattedPhone;
  }

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

  public String location() {
    return latitude + "," + longitude;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
