package com.foursquare.ui.venuedetail;

import com.foursquare.entity.VenueDetail;
import com.foursquare.model.ResponseCallback;
import com.foursquare.model.venue.VenueRepository;
import javax.inject.Inject;

public class VenueDetailPresenter implements VenueDetailContract.Presenter {
  private VenueDetailContract.View view;
  private VenueRepository venueRepository;

  @Inject public VenueDetailPresenter(VenueRepository venueRepository) {
    this.venueRepository = venueRepository;
  }

  @Override public void loadVenueDetails(String venueId) {
    view.setLoadingIndicator(true);
    venueRepository.venue(venueId, new ResponseCallback<VenueDetail>() {
      @Override public void onSuccess(VenueDetail venueDetail) {
        view.setLoadingIndicator(false);
        view.showVenueDetails(venueDetail);
      }

      @Override public void onFailure(String error) {
        view.setLoadingIndicator(false);
        view.showVenueDetailLoadError(error);
      }
    });
  }

  @Override public void takeView(VenueDetailContract.View v) {
    view = v;
  }

  @Override public void dropView() {
    view = null;
  }
}
