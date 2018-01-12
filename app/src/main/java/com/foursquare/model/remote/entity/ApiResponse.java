package com.foursquare.model.remote.entity;

public class ApiResponse<T> {
  T response;
  Meta meta;

  public T getResponse() {
    return response;
  }

  public void setResponse(T response) {
    this.response = response;
  }

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  class Meta {
    private int code;

    public int getCode() {
      return this.code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    private String requestId;

    public String getRequestId() {
      return this.requestId;
    }

    public void setRequestId(String requestId) {
      this.requestId = requestId;
    }
  }
}
