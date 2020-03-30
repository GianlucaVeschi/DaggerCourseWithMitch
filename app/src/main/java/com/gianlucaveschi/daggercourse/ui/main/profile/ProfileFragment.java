package com.gianlucaveschi.daggercourse.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gianlucaveschi.daggercourse.R;
import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.ui.auth.AuthResource;
import com.gianlucaveschi.daggercourse.ui.main.MainActivity;
import com.gianlucaveschi.daggercourse.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    //Logger
    private static final String TAG = "ProfileFragment";

    //Widgets
    private TextView email, username , website;

    //View Model
    private ProfileViewModel profileViewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment. " + this);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        profileViewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);

        /*NOTE:
        * Although it would be possible to do something like :
        * AuthResource<User> user = (MainActivity)getActivity().sessionManager.getAuthUser().getValue();
        * this would break the MVVM Architecture, because we don't observe the data.
        * Also this request would be made every time the View is created and this is not efficient.
        * */
        subscribeObservers();
    }

    private void subscribeObservers(){
        /*Fragments have their own life cycle, which is dictated by what the android system does.
        * This means that fragments could be deleted and recreated at any time and we don't want to
        * have any observer attached to a dead fragment floating in memory,
        * so we first removeObservers.
        * Another difference with activities is that we have to reference the LifeCycleOwner
        * */
        profileViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        profileViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){


                        case AUTHENTICATED:{
                            Log.d(TAG, "onChanged: ProfileFragment: AUTHENTICATED... " +
                                    "Authenticated as: " + userAuthResource.data.getEmail());
                            setUserDetails(userAuthResource.data);
                            break;
                        }

                        case ERROR:{
                            Log.d(TAG, "onChanged: ProfileFragment: ERROR...");
                            setErrorDetails(userAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setUserDetails(User user){
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        website.setText(user.getWebsite());
    }

    private void setErrorDetails(String message){
        email.setText(message);
        username.setText("error");
        website.setText("error");
    }
}
