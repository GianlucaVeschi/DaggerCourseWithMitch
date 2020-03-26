package com.gianlucaveschi.daggercourse.ui.auth;

import android.util.Log;

import com.gianlucaveschi.daggercourse.SessionManager;
import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    /**AppComponent contains the dependencies for the whole project so the following
     * objects are injected from there*/
    private final SessionManager sessionManager;    // @Singleton scoped dependency
    private final AuthApi authApi;                  // @AuthScope scoped dependency

    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.authApi = authApi;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

    }

    /**Called by the AuthActivity to make the Retrofit request.
     * Even though the request could is done directly here,
     * the ViewModel first communicates with the sessionManager,
     * which is the SINGLE SOURCE OF TRUTH*/
    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "attemptLogin: attempting to login.");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    /**Retrofit Request*/
    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(authApi.getUser(userId)

                // instead of calling onError, do this
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) throws Exception {
                        User errorUser = new User();
                        errorUser.setId(-1);
                        return errorUser;
                    }
                })

                // wrap User object in AuthResource
                .map(new Function<User, AuthResource<User>>() {
                    @Override
                    public AuthResource<User> apply(User user) throws Exception {
                        if(user.getId() == -1){
                            return AuthResource.error("Could not authenticate", null);
                        }
                        return AuthResource.authenticated(user);
                    }
                })
                .subscribeOn(Schedulers.io()));
    }

    private void testGetUser(int userID) {
        authApi.getUser(userID)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new io.reactivex.Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getId());
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

