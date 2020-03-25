package com.gianlucaveschi.daggercourse.ui.auth;

import android.util.Log;

import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    /**AppComponent contains the Retrofit instance and
     * given that AuthComponent is a sub component of AppComponent retrofit can also be used here*/
    private final AuthApi authApi;

    @Inject
    public AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

        testGetUser(5);

        //Source is the API call from the flowable object

        //ReactiveStream is the Publisher

    }

    private void testGetUser(int userID) {
        authApi.getUser(userID)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

