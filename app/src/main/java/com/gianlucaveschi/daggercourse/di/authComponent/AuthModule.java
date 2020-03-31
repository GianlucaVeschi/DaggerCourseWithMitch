package com.gianlucaveschi.daggercourse.di.authComponent;

import com.gianlucaveschi.daggercourse.network.auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideSessionApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

}
