package com.foursquare.network;

public class NetworkCredentials {
  private String apiUrl;
  private String apiVersion;
  private String clientId;
  private String clientSecret;

  public NetworkCredentials(String apiUrl, String apiVersion, String clientId,
      String clientSecret) {
    this.apiUrl = apiUrl;
    this.apiVersion = apiVersion;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }
}
