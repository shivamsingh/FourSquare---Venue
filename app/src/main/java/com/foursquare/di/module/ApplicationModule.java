package com.foursquare.di.module;

import com.foursquare.di.AppComponent;
import dagger.Module;

/**
 * This is a Dagger module.
 * We use this to bind our application class as Context in the AppComponent.
 * By using Dagger Android we do not need to pass our Application instance to any module.
 * One of the advantage of Dagger Android is that your
 * Application & Activities are provided into your graph for you.
 * {@link AppComponent}
 */
@Module
public abstract class ApplicationModule {
}
