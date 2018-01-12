package com.foursquare.ui.venues;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.foursquare.R;
import com.foursquare.entity.Venue;
import com.foursquare.ui.base.BaseListItemView;
import com.squareup.picasso.Picasso;

public class VenueItem extends RelativeLayout implements BaseListItemView<Venue> {
  @BindView(R.id.category_pic) ImageView categoryPic;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.distance) TextView distance;
  @BindView(R.id.rating) TextView rating;
  private Picasso picasso;
  private Venue venue;

  public VenueItem(Context context, Picasso picasso) {
    super(context);
    this.picasso = picasso;
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.venues_item, this);
    ButterKnife.bind(this);
  }

  @Override public void bind(Venue venue) {
    this.venue = venue;
    name.setText(venue.getName());
    distance.setText(String.valueOf(venue.formattedDistance()));
    rating.setText(String.valueOf(venue.getRating()));
    picasso.load(venue.getCategoryPic()).into(categoryPic);
  }

  public Venue getItem() {
    return venue;
  }
}
