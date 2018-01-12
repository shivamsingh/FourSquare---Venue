package com.foursquare.ui.venues;

import com.foursquare.di.scope.ActivityScoped;
import com.foursquare.di.scope.FragmentScoped;
import com.foursquare.model.venue.VenueRepository;
import com.foursquare.model.venue.remote.RemoteVenueRepository;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class VenuesModule {

  @FragmentScoped @ContributesAndroidInjector abstract VenuesFragment venuesFragment();

  @ActivityScoped @Binds abstract VenuesContract.Presenter presenter(VenuesPresenter presenter);

  @ActivityScoped @Binds
  abstract VenueRepository venueRepository(RemoteVenueRepository venueRepository);
}
