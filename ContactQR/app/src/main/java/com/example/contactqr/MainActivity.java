package com.example.contactqr;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes Bottom Nav with a Listener to Handle Item Selection
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Sets the First Selected Item of the Bottom Navigation to the Edit "Page" Starts a
        // New Fragment When App is Loaded
        bottomNav.setSelectedItemId(R.id.nav_edit);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditFragment()).commit();

    }

    /*
    *  This function handles changing the fragment_container to the correct fragment page.
    *  When an user clicks an item from the Bottom Navigation, the fragment_container is replaced
    *  with the corresponding fragment.
    */

    private BottomNavigationView.OnNavigationItemSelectedListener navListener;
    {

        navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {

                case R.id.nav_edit:
                    selectedFragment = new EditFragment();
                    break;
                case R.id.nav_scan:
                    selectedFragment = new ScanFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;

            }
        };
    }

}
