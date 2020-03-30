package com.gianlucaveschi.daggercourse.di.main;

import com.gianlucaveschi.daggercourse.network.main.MainApi;
import com.gianlucaveschi.daggercourse.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostRecyclerAdapter provideAdapter(){
        return new PostRecyclerAdapter();
    }
}
