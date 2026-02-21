/*
 * File: PreferencesActivity.java
 * Author(s): Arman
 * Purpose: Handles selection of dietary restrictions and allergens.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: UserRepository, CheckBox
 */
package com.example.designuxarman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.example.designuxarman.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;

public class PreferencesActivity extends AppCompatActivity {

    private CheckBox cbVegan, cbVegetarian, cbGlutenFree, cbDairyFree, cbNuts, cbSeafood;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        userRepository = new UserRepository(this);

        cbVegan = findViewById(R.id.cb_vegan);
        cbVegetarian = findViewById(R.id.cb_vegetarian);
        cbGlutenFree = findViewById(R.id.cb_gluten_free);
        cbDairyFree = findViewById(R.id.cb_dairy_free);
        cbNuts = findViewById(R.id.cb_nuts);
        cbSeafood = findViewById(R.id.cb_seafood);
        Button btnSave = findViewById(R.id.btn_save_preferences);

        loadExistingPreferences();

        btnSave.setOnClickListener(v -> savePreferences());
    }

    private void loadExistingPreferences() {
        Set<String> dietary = userRepository.getDietaryPreferences();
        Set<String> allergens = userRepository.getAllergenPreferences();

        cbVegan.setChecked(dietary.contains(getString(R.string.vegan)));
        cbVegetarian.setChecked(dietary.contains(getString(R.string.vegetarian)));
        cbGlutenFree.setChecked(dietary.contains(getString(R.string.gluten_free)));
        cbDairyFree.setChecked(dietary.contains(getString(R.string.dairy_free)));

        cbNuts.setChecked(allergens.contains(getString(R.string.nuts)));
        cbSeafood.setChecked(allergens.contains(getString(R.string.seafood)));
    }

    private void savePreferences() {
        Set<String> dietary = new HashSet<>();
        if (cbVegan.isChecked()) dietary.add(getString(R.string.vegan));
        if (cbVegetarian.isChecked()) dietary.add(getString(R.string.vegetarian));
        if (cbGlutenFree.isChecked()) dietary.add(getString(R.string.gluten_free));
        if (cbDairyFree.isChecked()) dietary.add(getString(R.string.dairy_free));

        Set<String> allergens = new HashSet<>();
        if (cbNuts.isChecked()) allergens.add(getString(R.string.nuts));
        if (cbSeafood.isChecked()) allergens.add(getString(R.string.seafood));

        userRepository.savePreferences(dietary, allergens);

        if (!isTaskRoot()) {
            // If it was opened from AccountFragment
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
