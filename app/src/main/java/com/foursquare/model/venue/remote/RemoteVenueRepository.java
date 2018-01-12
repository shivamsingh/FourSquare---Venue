package com.foursquare.model.venue.remote;

import com.foursquare.entity.Venue;
import com.foursquare.entity.VenueDetail;
import com.foursquare.model.ResponseCallback;
import com.foursquare.model.remote.entity.ApiResponse;
import com.foursquare.model.remote.entity.RemoteResponseCallback;
import com.foursquare.model.venue.VenueRepository;
import com.foursquare.network.venues.VenueService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RemoteVenueRepository implements VenueRepository {
  private VenueService venueService;

  @Inject RemoteVenueRepository(VenueService venueService) {
    this.venueService = venueService;
  }

  @Override
  public void venues(double lat, double lng, ResponseCallback<List<Venue>> responseCallback) {
    String location = String.valueOf(lat) + ',' + lng;
    venueService.venues(location)
        .enqueue(
            new RemoteResponseCallback<>(new ResponseCallback<ApiResponse<VenueListResponse>>() {
              @Override public void onSuccess(ApiResponse<VenueListResponse> venueListResponse) {
                responseCallback.onSuccess(mapToVenues(venueListResponse));
              }

              @Override public void onFailure(String error) {
                responseCallback.onFailure(error);
              }
            }));
  }

  @Override public void venue(String venueId, ResponseCallback<VenueDetail> responseCallback) {
    venueService.venue(venueId)
        .enqueue(
            new RemoteResponseCallback<>(new ResponseCallback<ApiResponse<VenueDetailResponse>>() {
              @Override
              public void onSuccess(ApiResponse<VenueDetailResponse> venueDetailResponse) {
                responseCallback.onSuccess(mapToVenueDetail(venueDetailResponse));
              }

              @Override public void onFailure(String error) {
                responseCallback.onFailure(error);
              }
            }));
  }

  private List<Venue> mapToVenues(ApiResponse<VenueListResponse> venueListResponse) {
    List<Venue> venues = new ArrayList<>();

    for (VenueListResponse.Group group : venueListResponse.getResponse().getGroups()) {
      for (VenueListResponse.Item item : group.getItems()) {
        com.foursquare.model.venue.remote.Venue itemVenue = item.getVenue();

        Venue venue = new Venue();

        venue.setId(itemVenue.getId());
        venue.setCategoryPic(itemVenue.getCategories().get(0).picUrl_88());
        venue.setName(itemVenue.getName());
        venue.setDistance(itemVenue.getLocation().getDistance());
        venue.setRating(itemVenue.getRating());

        venues.add(venue);
      }
    }

    return venues;
  }

  private VenueDetail mapToVenueDetail(ApiResponse<VenueDetailResponse> venueDetailResponse) {
    com.foursquare.model.venue.remote.Venue itemVenue =
        venueDetailResponse.getResponse().getVenue();

    VenueDetail venueDetail = new VenueDetail();

    venueDetail.setId(itemVenue.getId());
    venueDetail.setCategoryPic(itemVenue.getCategories().get(0).picUrl_88());
    venueDetail.setName(itemVenue.getName());
    venueDetail.setDistance(itemVenue.getLocation().getDistance());
    venueDetail.setRating(itemVenue.getRating());
    if (itemVenue.getBestPhoto() != null) {
      venueDetail.setBestPhoto(itemVenue.getBestPhoto().getUrl());
    }
    venueDetail.setFormattedAddress(itemVenue.getLocation().formattedAddress());
    venueDetail.setLatitude(itemVenue.getLocation().getLat());
    venueDetail.setLongitude(itemVenue.getLocation().getLng());
    if (itemVenue.getContact() != null) {
      venueDetail.setFormattedPhone(itemVenue.getContact().formattedPhone());
      venueDetail.setPhone(itemVenue.getContact().getPhone());
    } else {
      venueDetail.setHoursStatus("N/A");
    }
    if (itemVenue.getHours() != null) {
      venueDetail.setHoursStatus(itemVenue.getHours().status());
    } else {
      venueDetail.setHoursStatus("N/A");
    }

    return venueDetail;
  }
}
