package com.foursquare.di.module;

import com.foursquare.network.NetworkCredentialInterceptor;
import com.foursquare.network.NetworkCredentials;
import com.foursquare.utils.Constant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetworkModule {

  @Provides @Singleton NetworkCredentials networkCredentials() {
    return new NetworkCredentials(Constant.API_URL_BASE, Constant.API_VERSION, Constant.CLIENT_ID,
        Constant.CLIENT_SECRET);
  }

  @Provides @Singleton public Gson gson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return gsonBuilder.create();
  }

  @Provides @Singleton OkHttpClient httpClient(NetworkCredentials networkCredentials) {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    return new OkHttpClient().newBuilder()
        .addInterceptor(new NetworkCredentialInterceptor(networkCredentials))
        .addInterceptor(logging)
        .build();
  }

  @Provides @Singleton protected Retrofit retrofit(Gson gson, OkHttpClient okHttpClient,
      NetworkCredentials networkCredentials) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(networkCredentials.getApiUrl())
        .client(okHttpClient)
        .build();
  }
}
