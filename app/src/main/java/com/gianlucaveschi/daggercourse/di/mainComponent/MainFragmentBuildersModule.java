package com.gianlucaveschi.daggercourse.di.mainComponent;

import com.gianlucaveschi.daggercourse.ui.main.posts.PostsFragment;
import com.gianlucaveschi.daggercourse.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//This Fragment will only exist within the scope of the MainActivity SubComponent
@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();   //SubComponent

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();       //SubComponent

}