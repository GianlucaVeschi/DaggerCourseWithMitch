package com.gianlucaveschi.daggercourse.di;

import com.gianlucaveschi.daggercourse.viewmodels.ViewModelProviderFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

/**Responsible for generating the dependencies during the dependency injection*/
@Module
public abstract class ViewModelFactoryModule {

    /**Binds is Similar to @Provides but it doesn't need a body*/
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}