package com.foursquare.model.venue.remote;

class Contact {
  private String formattedPhone;
  private String phone;

  public String getFormattedPhone() {
    return formattedPhone;
  }

  public void setFormattedPhone(String formattedPhone) {
    this.formattedPhone = formattedPhone;
  }

  public String formattedPhone() {
    return formattedPhone != null ? formattedPhone : "N/A";
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
