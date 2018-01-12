package com.foursquare.model.remote.entity;

import retrofit2.Response;

public interface RemoteErrorResponse {

    String errorMessage(Response response);
}
