package com.gianlucaveschi.daggercourse.ui.main;

import android.os.Bundle;

import com.gianlucaveschi.daggercourse.BaseActivity;
import com.gianlucaveschi.daggercourse.R;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
