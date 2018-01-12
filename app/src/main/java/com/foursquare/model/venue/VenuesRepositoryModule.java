package com.foursquare.model.venue;

import com.foursquare.di.scope.ActivityScoped;
import com.foursquare.network.venues.VenueService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module public class VenuesRepositoryModule {

  @ActivityScoped @Provides VenueService venueService(Retrofit retrofit) {
    return retrofit.create(VenueService.class);
  }
}
