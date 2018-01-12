package com.foursquare.model.venue.remote;

class Hours {
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String status() {
    return status != null ? status : "N/A";
  }
}
