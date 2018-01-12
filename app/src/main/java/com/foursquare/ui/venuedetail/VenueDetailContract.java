package com.foursquare.ui.venuedetail;

import com.foursquare.entity.VenueDetail;
import com.foursquare.ui.base.BasePresenter;
import com.foursquare.ui.base.BaseView;
import com.foursquare.ui.venues.VenuesContract;

public interface VenueDetailContract {
  interface View extends BaseView {

    void showVenueDetails(VenueDetail venueDetail);

    void showVenueDetailLoadError(String error);
  }

  interface Presenter extends BasePresenter {

    void loadVenueDetails(String venueId);

    void takeView(VenueDetailContract.View v);

    void dropView();
  }
}
