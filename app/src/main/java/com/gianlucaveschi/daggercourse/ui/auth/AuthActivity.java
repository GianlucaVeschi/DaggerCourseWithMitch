package com.gianlucaveschi.daggercourse.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.gianlucaveschi.daggercourse.R;
import com.gianlucaveschi.daggercourse.models.User;
import com.gianlucaveschi.daggercourse.ui.auth.AuthViewModel;
import com.gianlucaveschi.daggercourse.ui.main.MainActivity;
import com.gianlucaveschi.daggercourse.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;
    private EditText userId;
    private ProgressBar progressBar;

    //Dependencies injected from the AppModule
    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);

        /**Instantiate the ViewModel by providing a Factory*/
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        //Logo injected as a Drawable
        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case LOADING:{
                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,
                                    "WELCOME " + userAuthResource.data.getUsername(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }

                        case ERROR:{
                            Log.e(TAG, "onChanged: " + userAuthResource.message);
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,
                                    userAuthResource.message + "\nDid you enter a number between 0 and 10?",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess(){
        Log.d(TAG, "onLoginSuccess: login successful!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {
        requestManager
                .load(logo) //injected from the AuthComponent
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:{
                attemptLogin();
            }
        }
    }

    private void attemptLogin() {
        Log.d(TAG, "attemptLogin: attemptLogin");
        if(TextUtils.isEmpty(userId.getText().toString())){
            Log.d(TAG, "attemptLogin: failed");
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
