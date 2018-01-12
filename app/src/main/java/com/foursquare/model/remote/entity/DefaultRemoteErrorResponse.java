package com.foursquare.model.remote.entity;

import com.foursquare.di.module.NetworkModule;
import com.foursquare.utils.Constant;
import retrofit2.Response;

public class DefaultRemoteErrorResponse implements RemoteErrorResponse {

    @Override
    public String errorMessage(Response response) {
        RemoteError remoteError = null;
        try {
            remoteError = new NetworkModule().gson()
                    .fromJson(response.errorBody().string(), RemoteError.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (remoteError == null || remoteError.getErrorDescription().equals(""))
            return Constant.UNKNOWN_ERROR_MESSAGE;
        return remoteError.getErrorDescription();
    }
}
