package com.foursquare.network.venues;

import com.foursquare.model.remote.entity.ApiResponse;
import com.foursquare.model.venue.remote.VenueDetailResponse;
import com.foursquare.model.venue.remote.VenueListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VenueService {

  @GET("v2/venues/explore") Call<ApiResponse<VenueListResponse>> venues(
      @Query("ll") String location);

  @GET("v2/venues/{venue_id}") Call<ApiResponse<VenueDetailResponse>> venue(
      @Path("venue_id") String venueId);
}
