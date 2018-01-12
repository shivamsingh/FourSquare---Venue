package com.foursquare.di.scope;

import com.foursquare.di.AppComponent;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * In Dagger, an unscoped component cannot depend on a scoped component.
 * As {@link AppComponent} is a scoped {@code @Singleton}, we create custom scope
 * to be used by all Activity.
 * Additionally, a component with specific scope cannot have sub component with same scope}
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
