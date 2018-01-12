package com.foursquare.model.venue.remote;

import java.util.List;

public class VenueListResponse {
  private List<Group> groups;

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }

  public class Group {
    private List<Item> items;

    public List<Item> getItems() {
      return items;
    }

    public void setItems(List<Item> items) {
      this.items = items;
    }
  }

  public class Item {
    private Venue venue;

    public Venue getVenue() {
      return venue;
    }

    public void setVenue(Venue venue) {
      this.venue = venue;
    }
  }
}
