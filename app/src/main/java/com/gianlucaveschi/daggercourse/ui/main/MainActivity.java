package com.gianlucaveschi.daggercourse.ui.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gianlucaveschi.daggercourse.BaseActivity;
import com.gianlucaveschi.daggercourse.R;
import com.gianlucaveschi.daggercourse.SessionManager;
import com.gianlucaveschi.daggercourse.models.Post;
import com.gianlucaveschi.daggercourse.ui.main.posts.PostsFragment;
import com.gianlucaveschi.daggercourse.ui.main.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    //UI Widgets
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        init();

    }

    private void init(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*If the User is already inside a fragment and wants to go the same fragment again,
    * the fragment should not be added to the backstack.
    * This method makes sure that the user goes to a destination different
    * from the one he's already inside. */
    public boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    //Enables the Hamburger Icon to open the NavDrawer but doesn't allow to close it
    //Enabl
    // es the back arrow
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this,R.id.nav_host_fragment),
                drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:{
                sessionManager.logOut();
                return true;
            }
            case R.id.home:{
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true; //Consume the click
                }
                else{
                    return false; //Don't consume the click
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:{

                // nav options to clear backstack
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main, true)
                        .build();

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.profileScreen,
                                null,
                                navOptions
                        );
                break;
            }
            case R.id.nav_posts:{
                if(isValidDestination(R.id.postsScreen)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.postsScreen);
                }
                break;
            }
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}
