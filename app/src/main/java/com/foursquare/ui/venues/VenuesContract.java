package com.foursquare.ui.venues;

import com.foursquare.entity.Venue;
import com.foursquare.ui.base.BasePresenter;
import com.foursquare.ui.base.BaseView;
import java.util.List;

public interface VenuesContract {

  interface View extends BaseView {

    void showVenues(List<Venue> venues);

    void showLoadVenuesError();

    void showVenueDetailsUi(String venueId);
  }

  interface Presenter extends BasePresenter {

    void loadVenues(double lat, double lng);

    void takeView(View v);

    void dropView();

    void openVenueDetail(Venue requestedVenue);

    void setSorting(VenueSortType sortType);
  }
}
