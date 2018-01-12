package com.foursquare.ui.venues;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.foursquare.R;
import com.foursquare.entity.Venue;
import com.foursquare.ui.base.BaseFragment;
import com.foursquare.ui.venuedetail.VenueDetailActivity;
import com.foursquare.ui.widget.FoursquareRecyclerAdapter;
import com.foursquare.utils.DisposableManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation2.ReactiveLocationProviderConfiguration;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class VenuesFragment extends BaseFragment implements VenuesContract.View {
  private static final int REQUEST_CHECK_SETTINGS = 1001;
  private static final String TAG = "VenuesFragment";

  @Inject VenuesContract.Presenter presenter;
  @Inject Picasso picasso;

  @BindView(R.id.refresh) SwipeRefreshLayout refresh;
  @BindView(R.id.venues) RecyclerView venuesList;
  @BindView(R.id.toolbar) Toolbar toolbar;

  private Observable<Location> lastKnownLocationObservable;
  private Observable<Location> locationUpdatesObservable;

  private Location location;
  private ReactiveLocationProvider locationProvider;
  private FoursquareRecyclerAdapter venuesAdapter;

  ArrayList<Venue> venues = null;

  @Inject public VenuesFragment() {
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.venues_frag, container, false);
    ButterKnife.bind(this, root);
    init();
    return root;
  }

  private void init() {
    toolbar.setTitle(getString(R.string.app_name));
    toolbar.inflateMenu(R.menu.venues);
    toolbar.setOnMenuItemClickListener(this::optionMenuSelected);
    refresh.setOnRefreshListener(this::refreshVenues);
  }

  private boolean optionMenuSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.refresh:
        refreshVenues();
        return true;

      case R.id.sort_by:
        showSortByMenu();
        return true;
    }
    return false;
  }

  private void showSortByMenu() {
    AlertDialog.Builder builderSingle;
    builderSingle = new AlertDialog.Builder(getContext());
    builderSingle.setTitle("Sort By");
    final ArrayAdapter<String> arrayAdapter =
        new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_singlechoice);
    arrayAdapter.add("Default");
    arrayAdapter.add("Distance");
    arrayAdapter.add("Rating");
    builderSingle.setAdapter(arrayAdapter, this::sortOptionSelected);
    builderSingle.show();
  }

  private void sortOptionSelected(DialogInterface dialog, int which) {
    switch (which) {
      case 0:
        presenter.setSorting(VenueSortType.DEFAULT);
        break;
      case 1:
        presenter.setSorting(VenueSortType.DISTANCE);
        break;
      case 2:
        presenter.setSorting(VenueSortType.RATING);
        break;
    }
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null) {
      ArrayList<Venue> venues = savedInstanceState.getParcelableArrayList("venues");
      if (venues != null) {
        bindVenues();
        if (venuesAdapter != null) venuesList.setAdapter(venuesAdapter);
      }
    }
  }

  private void bindVenues() {
    if (venuesAdapter == null) {
      venuesAdapter = new FoursquareRecyclerAdapter<>(venues, this::venueItem);
      venuesList.setAdapter(venuesAdapter);
    } else {
      venuesAdapter.setItems(venues);
    }
  }

  @NonNull private VenueItem venueItem() {
    VenueItem venueItem = new VenueItem(getActivity(), picasso);
    venueItem.setOnClickListener(view -> venueItemSelected(venueItem));
    return venueItem;
  }

  private void venueItemSelected(VenueItem venueItem) {
    presenter.openVenueDetail(venueItem.getItem());
  }

  @Override public void onResume() {
    super.onResume();
    presenter.takeView(this);
  }

  @SuppressLint("MissingPermission") private void initLocationObservations() {
    locationProvider = new ReactiveLocationProvider(getContext(),
        ReactiveLocationProviderConfiguration.builder()
            .setRetryOnConnectionSuspended(true)
            .build());

    lastKnownLocationObservable =
        locationProvider.getLastKnownLocation().observeOn(AndroidSchedulers.mainThread());

    final LocationRequest locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setNumUpdates(1)
        .setInterval(100);

    locationUpdatesObservable = locationProvider.checkLocationSettings(
        new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build())
        .doOnNext(locationSettingsResult -> {
          Status status = locationSettingsResult.getStatus();
          if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
            try {
              status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException th) {
              Log.e(TAG, "Error opening settings activity.", th);
            }
          }
        })
        .flatMap(locationSettingsResult -> locationProvider.getUpdatedLocation(locationRequest))
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public void onStart() {
    super.onStart();
    initLocationObservations();
    checkLocationPermission();
  }

  @Override public void onStop() {
    super.onStop();
    DisposableManager.dispose();
  }

  private void checkLocationPermission() {
    new RxPermissions(getActivity()).request(Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe(granted -> {
          if (granted) {
            onLocationPermissionGranted();
          } else {
            showNoLocationPermissionView();
          }
        });
  }

  private void onLocationPermissionGranted() {
    DisposableManager.add(
        lastKnownLocationObservable.subscribe(this::locationFound, Throwable::printStackTrace));

    DisposableManager.add(
        locationUpdatesObservable.subscribe(this::locationFound, Throwable::printStackTrace));
  }

  private void locationFound(Location location) {
    if (location == null) return;

    this.location = location;
    if (venuesAdapter == null) refreshVenues();
    DisposableManager.dispose();
  }

  private void refreshVenues() {
    if (location != null) presenter.loadVenues(location.getLatitude(), location.getLongitude());
  }

  private void showNoLocationPermissionView() {
    Toast.makeText(getActivity(), "No demo without location permissions :(.", Toast.LENGTH_LONG)
        .show();
  }

  @Override public void showVenues(List<Venue> venues) {
    refresh.setRefreshing(false);
    this.venues = (ArrayList<Venue>) venues;
    bindVenues();
  }

  @Override public void showLoadVenuesError() {
    refresh.setRefreshing(false);
    Toast.makeText(getActivity(), "Venues not loaded.", Toast.LENGTH_LONG).show();
  }

  @Override public void showVenueDetailsUi(String venueId) {
    Intent intent = new Intent(getContext(), VenueDetailActivity.class);
    intent.putExtra(VenueDetailActivity.EXTRA_VENUE_ID, venueId);
    startActivity(intent);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (venuesAdapter != null) {
      outState.putParcelableArrayList("venues", (ArrayList<Venue>) venuesAdapter.getItems());
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
    switch (requestCode) {
      case REQUEST_CHECK_SETTINGS:
        switch (resultCode) {
          case RESULT_OK:
            // All required changes were successfully made
            Log.d(TAG, "User enabled location");
            break;
          case RESULT_CANCELED:
            // The user was asked to change settings, but chose not to
            Log.d(TAG, "User Cancelled enabling location");
            break;
          default:
            break;
        }
        break;
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.dropView();
  }
}
