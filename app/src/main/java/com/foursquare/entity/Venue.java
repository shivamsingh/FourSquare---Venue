package com.foursquare.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.foursquare.utils.FormatUtils;

public class Venue implements Parcelable {
  private String id;
  private String categoryPic;
  private int distance;
  private float rating;
  private String name;

  public Venue() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategoryPic() {
    return categoryPic;
  }

  public void setCategoryPic(String categoryPic) {
    this.categoryPic = categoryPic;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public float getRating() {
    return rating;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Venue(Parcel in) {
    id = in.readString();
    categoryPic = in.readString();
    distance = in.readInt();
    rating = in.readFloat();
    name = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(id);
    parcel.writeString(categoryPic);
    parcel.writeInt(distance);
    parcel.writeFloat(rating);
    parcel.writeString(name);
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public Venue createFromParcel(Parcel in) {
      return new Venue(in);
    }

    public Venue[] newArray(int size) {
      return new Venue[size];
    }
  };

  public String formattedDistance() {
    return FormatUtils.formatMeterInKm(distance);
  }
}
