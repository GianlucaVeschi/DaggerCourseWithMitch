package com.gianlucaveschi.daggercourse;

import android.util.Log;

import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    /**We use LiveData so that the AuthenticatedUser can be observed by any class
     * where the sessionManager is injected into.*/
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {

    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source) {
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((User)null));
            //Starts to listen to the given source, (state of the user).
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<User>logout());
    }


    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUser;
    }

}
