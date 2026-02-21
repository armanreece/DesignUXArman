/*
 * File: AuthActivity.java
 * Author(s): Arman
 * Purpose: Host for Login and Signup fragments.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: AppCompatActivity, LoginFragment, SignupFragment
 */
package com.example.designuxarman;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.designuxarman.repository.UserRepository;

public class AuthActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userRepository = new UserRepository(this);

        if (userRepository.isLoggedIn()) {
            startNextActivity();
            return;
        }

        if (savedInstanceState == null) {
            showLogin();
        }
    }

    public void showLogin() {
        replaceFragment(new LoginFragment());
    }

    public void showSignup() {
        replaceFragment(new SignupFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void onAuthSuccess() {
        startNextActivity();
    }

    private void startNextActivity() {
        if (userRepository.hasSetPreferences()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, PreferencesActivity.class));
        }
        finish();
    }
}
