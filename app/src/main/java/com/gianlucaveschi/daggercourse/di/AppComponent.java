package com.gianlucaveschi.daggercourse.di;

import android.app.Application;

import com.gianlucaveschi.daggercourse.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class
        })

public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        //Binding the application object when the AppComponent is created.
        //By doing this, the application is also available in the module components
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
