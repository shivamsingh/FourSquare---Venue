package com.foursquare.ui.venuedetail;

import com.foursquare.di.scope.ActivityScoped;
import com.foursquare.di.scope.FragmentScoped;
import com.foursquare.model.venue.VenueRepository;
import com.foursquare.model.venue.remote.RemoteVenueRepository;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class VenueDetailModule {

  @FragmentScoped @ContributesAndroidInjector abstract VenueDetailFragment venueDetailFragment();

  @ActivityScoped @Binds
  abstract VenueDetailContract.Presenter presenter(VenueDetailPresenter presenter);

  @ActivityScoped @Binds
  abstract VenueRepository venueRepository(RemoteVenueRepository venueRepository);
}
