package com.gianlucaveschi.daggercourse.ui.main.profile;

import android.util.Log;

import com.gianlucaveschi.daggercourse.SessionManager;
import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.ui.auth.AuthResource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**This View model is added as a dependency of MainViewModelsModule*/
public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    public final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: viewmodel is ready...");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
