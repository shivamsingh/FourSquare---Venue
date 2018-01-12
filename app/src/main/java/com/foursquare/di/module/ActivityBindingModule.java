package com.foursquare.di.module;

import com.foursquare.di.scope.ActivityScoped;
import com.foursquare.model.venue.VenuesRepositoryModule;
import com.foursquare.ui.venuedetail.VenueDetailActivity;
import com.foursquare.ui.venuedetail.VenueDetailModule;
import com.foursquare.ui.venues.VenuesActivity;
import com.foursquare.ui.venues.VenuesModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class ActivityBindingModule {

  @ActivityScoped
  @ContributesAndroidInjector(modules = { VenuesModule.class, VenuesRepositoryModule.class })
  abstract VenuesActivity venuesActivity();

  @ActivityScoped
  @ContributesAndroidInjector(modules = { VenueDetailModule.class, VenuesRepositoryModule.class })
  abstract VenueDetailActivity venueDetailActivity();
}
