package com.foursquare.ui.venues;

import android.os.Bundle;
import com.foursquare.R;
import com.foursquare.ui.base.BaseActivity;
import com.foursquare.utils.ActivityUtils;
import dagger.Lazy;
import javax.inject.Inject;

public class VenuesActivity extends BaseActivity {

  @Inject Lazy<VenuesFragment> venuesFragmentLazy;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.container_act);

    VenuesFragment venuesFragment =
        (VenuesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    if (venuesFragment == null) {
      venuesFragment = venuesFragmentLazy.get();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), venuesFragment,
          R.id.content_frame);
      venuesFragment.setRetainInstance(true);
    }
  }
}
