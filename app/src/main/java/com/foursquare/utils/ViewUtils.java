package com.foursquare.utils;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class ViewUtils {

  public static void underline(TextView textView){
    SpannableString content = new SpannableString(textView.getText());
    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
    textView.setText(content);
  }
}
