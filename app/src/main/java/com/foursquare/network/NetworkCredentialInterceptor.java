package com.foursquare.network;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkCredentialInterceptor implements Interceptor {
  private NetworkCredentials credentials;

  public NetworkCredentialInterceptor(NetworkCredentials credentials) {
    this.credentials = credentials;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    HttpUrl url = chain.request()
        .url()
        .newBuilder()
        .addQueryParameter("client_id", credentials.getClientId())
        .addQueryParameter("client_secret", credentials.getClientSecret())
        .addQueryParameter("v", credentials.getApiVersion())
        .build();
    return chain.proceed(chain.request().newBuilder().url(url).build());
  }
}
