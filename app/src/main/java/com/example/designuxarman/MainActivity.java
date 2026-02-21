/*
 * File: MainActivity.java
 * Author(s): Arman
 * Purpose: Main container with BottomNavigationView.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: BottomNavigationView, DiscoveryFragment, CookbookFragment, AccountFragment
 */
package com.example.designuxarman;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_discover) {
                selectedFragment = new DiscoveryFragment();
            } else if (id == R.id.nav_cookbook) {
                selectedFragment = new CookbookFragment();
            } else if (id == R.id.nav_account) {
                selectedFragment = new AccountFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Set default selection
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_discover);
        }
    }
}
