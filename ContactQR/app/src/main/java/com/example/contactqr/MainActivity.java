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

        // Initializes Bottom Nav
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_code);

        // Starts a New Fragment When App is Loaded
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CodeFragment()).commit();

    }

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
                    case R.id.nav_code:
                        selectedFragment = new CodeFragment();
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
