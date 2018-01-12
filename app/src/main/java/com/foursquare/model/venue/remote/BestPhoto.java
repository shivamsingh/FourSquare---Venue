package com.foursquare.model.venue.remote;

class BestPhoto {
  private String prefix;
  private String suffix;
  private int width;
  private int height;

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  String getUrl() {
    return prefix + width + 'x' + height + suffix;
  }
}
