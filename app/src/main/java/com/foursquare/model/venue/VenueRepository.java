package com.foursquare.model.venue;

import com.foursquare.entity.Venue;
import com.foursquare.entity.VenueDetail;
import com.foursquare.model.ResponseCallback;
import java.util.List;

public interface VenueRepository {

  void venues(double lat, double lng, ResponseCallback<List<Venue>> responseCallback);

  void venue(String venueId, ResponseCallback<VenueDetail> responseCallback);
}
