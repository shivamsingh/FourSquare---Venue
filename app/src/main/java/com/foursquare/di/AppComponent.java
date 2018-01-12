package com.foursquare.di;

import android.app.Application;
import com.foursquare.FoursquareApplication;
import com.foursquare.di.module.ActivityBindingModule;
import com.foursquare.di.module.ApplicationModule;
import com.foursquare.di.module.CommonModule;
import com.foursquare.di.module.NetworkModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    NetworkModule.class,
    CommonModule.class,
    ActivityBindingModule.class,
    AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication>{

  void inject(FoursquareApplication foursquareApplication);

  @Component.Builder
  interface Builder {

    @BindsInstance
    AppComponent.Builder application(Application application);

    AppComponent build();
  }
}
