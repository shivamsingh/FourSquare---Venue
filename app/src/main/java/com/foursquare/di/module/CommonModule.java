package com.foursquare.di.module;

import android.app.Application;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module public class CommonModule {

  @Provides @Singleton protected Picasso picasso(Application application, OkHttpClient client) {
    return new Picasso.Builder(application.getApplicationContext()).downloader(
        new OkHttp3Downloader(client)).build();
  }
}
