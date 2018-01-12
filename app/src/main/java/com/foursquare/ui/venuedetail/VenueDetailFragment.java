package com.foursquare.ui.venuedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.foursquare.R;
import com.foursquare.entity.VenueDetail;
import com.foursquare.ui.base.BaseFragment;
import com.foursquare.utils.ActionUtils;
import com.foursquare.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class VenueDetailFragment extends BaseFragment implements VenueDetailContract.View {
  public static final String EXTRA_VENUE_ID = "EXTRA_VENUE_ID";

  @Inject VenueDetailContract.Presenter presenter;
  @Inject Picasso picasso;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.best_pic) ImageView bestPic;
  @BindView(R.id.rating) TextView rating;
  @BindView(R.id.hours) TextView hours;
  @BindView(R.id.contact) TextView contact;
  @BindView(R.id.address) TextView address;

  private String venueId;
  private VenueDetail venueDetail;

  @Inject public VenueDetailFragment() {
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.venue_detail_frag, container, false);
    ButterKnife.bind(this, root);
    init();
    return root;
  }

  private void init() {
    toolbar.setNavigationIcon(R.drawable.ic_back_white);
    toolbar.setNavigationOnClickListener(view -> getActivity().finish());

    readArguments();

    presenter.takeView(this);
    presenter.loadVenueDetails(venueId);
  }

  private void readArguments() {
    venueId = getArguments().getString(EXTRA_VENUE_ID);
  }

  @OnClick(R.id.address) void addressSelected() {
    ActionUtils.openMap(getActivity(), venueDetail.getLatitude(), venueDetail.getLongitude(), venueDetail.getName());
  }

  @OnClick(R.id.contact) void contactSelected() {
    if (venueDetail.getPhone() != null) ActionUtils.call(getActivity(), venueDetail.getPhone());
  }

  @Override public void showVenueDetails(VenueDetail venueDetail) {
    this.venueDetail = venueDetail;

    toolbar.setTitle(venueDetail.getName());
    rating.setText(String.valueOf(venueDetail.getRating()));
    hours.setText(String.valueOf(venueDetail.getHoursStatus()));
    contact.setText(String.valueOf(venueDetail.getFormattedPhone()));
    address.setText(venueDetail.getFormattedAddress());
    picasso.load(venueDetail.getBestPhoto()).into(bestPic);

    ViewUtils.underline(address);
    ViewUtils.underline(contact);
  }

  @Override public void showVenueDetailLoadError(String error) {
    Toast.makeText(getActivity(), "Venue not loaded.", Toast.LENGTH_LONG).show();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter.dropView();
  }
}

