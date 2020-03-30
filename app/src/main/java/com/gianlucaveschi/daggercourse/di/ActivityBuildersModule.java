package com.gianlucaveschi.daggercourse.di;

import com.gianlucaveschi.daggercourse.di.auth.AuthModule;
import com.gianlucaveschi.daggercourse.di.auth.AuthViewModelsModule;
import com.gianlucaveschi.daggercourse.di.main.MainFragmentBuildersModule;
import com.gianlucaveschi.daggercourse.di.main.MainModule;
import com.gianlucaveschi.daggercourse.di.main.MainViewModelsModule;
import com.gianlucaveschi.daggercourse.ui.auth.AuthActivity;
import com.gianlucaveschi.daggercourse.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    /**Module scoped here because only AuthActivity should use AuthViewModel*/
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class
            })
    abstract AuthActivity contributeAuthActivity(); //SubComponent

    @ContributesAndroidInjector(
            modules  = {
                    MainFragmentBuildersModule.class,
                    MainViewModelsModule.class,
                    MainModule.class
            })
    abstract MainActivity contributeMainActivity(); //SubComponent
}
