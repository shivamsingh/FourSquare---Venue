package com.foursquare.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActionUtils {

  public static void openMap(Context context, double lat, double lng, String name) {
    String location = lat + "," + lng;
    Uri gmmIntentUri = Uri.parse("geo:" + location + "?q=" + location + "(" + name + ")");
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(mapIntent);
    }
  }

  public static void call(Context context, String phone) {
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
    context.startActivity(intent);
  }
}
