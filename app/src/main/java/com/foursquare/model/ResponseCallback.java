package com.foursquare.model;

public interface ResponseCallback<T> {

  void onSuccess(T t);

  void onFailure(String error);
}
