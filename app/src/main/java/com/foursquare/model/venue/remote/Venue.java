package com.foursquare.model.venue.remote;

import com.foursquare.utils.FormatUtils;
import java.util.List;

public class Venue {
  private String id;
  private String name;
  private Location location;
  private float rating;
  private List<Category> categories;
  private BestPhoto bestPhoto;
  private Contact contact;
  private Hours hours;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public BestPhoto getBestPhoto() {
    return bestPhoto;
  }

  public Contact getContact() {
    return contact;
  }

  public Hours getHours() {
    return hours;
  }

  public void setHours(Hours hours) {
    this.hours = hours;
  }
}