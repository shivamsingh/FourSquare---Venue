package com.foursquare.ui.venues;

import android.support.annotation.NonNull;
import com.foursquare.entity.Venue;
import com.foursquare.model.ResponseCallback;
import com.foursquare.model.venue.VenueRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

public class VenuesPresenter implements VenuesContract.Presenter {
  private VenuesContract.View venueView;
  private VenueRepository venueRepository;
  private VenueSortType currentSortType = VenueSortType.DEFAULT;
  private List<Venue> venues;

  @Inject public VenuesPresenter(VenueRepository venueRepository) {
    this.venueRepository = venueRepository;
  }

  @Override public void loadVenues(double lat, double lng) {
    venueView.setLoadingIndicator(true);
    venueRepository.venues(lat, lng, new ResponseCallback<List<Venue>>() {
      @Override public void onSuccess(List<Venue> venues) {
        VenuesPresenter.this.venues = venues;
        venueView.setLoadingIndicator(false);
        venueView.showVenues(arrangedVenuesForCurrentSortType(venues));
      }

      @Override public void onFailure(String error) {
        venueView.setLoadingIndicator(false);
        venueView.showLoadVenuesError();
      }
    });
  }

  private List<Venue> arrangedVenuesForCurrentSortType(List<Venue> venues) {
    List<Venue> venueList = new ArrayList<>();
    for (Venue venue : venues)
      venueList.add(venue);

    switch (currentSortType) {
      case RATING:
        Collections.sort(venueList, new RatingComparator());
        break;
      case DISTANCE:
        Collections.sort(venueList, new DistanceComparator());
        break;
    }
    return venueList;
  }

  @Override public void takeView(VenuesContract.View view) {
    venueView = view;
  }

  @Override public void dropView() {
    venueView = null;
  }

  @Override public void openVenueDetail(@NonNull Venue requestedVenue) {
    venueView.showVenueDetailsUi(requestedVenue.getId());
  }

  @Override public void setSorting(VenueSortType sortType) {
    this.currentSortType = sortType;
    if (venues != null) venueView.showVenues(arrangedVenuesForCurrentSortType(venues));
  }

  private static class DistanceComparator implements Comparator<Venue> {
    @Override public int compare(Venue v1, Venue v2) {
      if (v1.getDistance() < v2.getDistance()) return -1;
      if (v1.getDistance() > v2.getDistance()) return 1;
      return 0;
    }
  }

  private static class RatingComparator implements Comparator<Venue> {
    @Override public int compare(Venue v1, Venue v2) {
      if (v1.getRating() > v2.getRating()) return -1;
      if (v1.getRating() < v2.getRating()) return 1;
      return 0;
    }
  }
}
