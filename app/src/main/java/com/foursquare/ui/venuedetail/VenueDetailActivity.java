package com.foursquare.ui.venuedetail;

import android.os.Bundle;
import com.foursquare.R;
import com.foursquare.ui.base.BaseActivity;
import com.foursquare.utils.ActivityUtils;
import dagger.Lazy;
import javax.inject.Inject;

public class VenueDetailActivity extends BaseActivity {
  public static final String EXTRA_VENUE_ID = "EXTRA_VENUE_ID";

  @Inject Lazy<VenueDetailFragment> venueDetailFragmentLazy;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.container_act);

    VenueDetailFragment venueDetailFragment =
        (VenueDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    if (venueDetailFragment == null) {
      venueDetailFragment = venueDetailFragmentLazy.get();
      venueDetailFragment.setArguments(bundleWithVenueId());
      venueDetailFragment.setRetainInstance(true);
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), venueDetailFragment,
          R.id.content_frame);
    }
  }

  private Bundle bundleWithVenueId() {
    Bundle bundle = new Bundle();
    bundle.putString(VenueDetailFragment.EXTRA_VENUE_ID,
        getIntent().getStringExtra(EXTRA_VENUE_ID));
    return bundle;
  }
}
