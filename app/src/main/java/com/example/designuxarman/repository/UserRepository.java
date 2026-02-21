/*
 * File: UserRepository.java
 * Author(s): Arman
 * Purpose: Manages user session, credentials, and preferences using SharedPreferences.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: SharedPreferences, Context
 */
package com.example.designuxarman.repository;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_HAS_PREFERENCES = "has_preferences";
    private static final String KEY_DIETARY = "dietary_prefs";
    private static final String KEY_ALLERGENS = "allergens_prefs";
    private static final String KEY_SAVED_RECIPES = "saved_recipes";
    private static final String KEY_DISLIKED_RECIPES = "disliked_recipes";

    private final SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean signup(String email, String password) {
        return sharedPreferences.edit()
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .commit();
    }

    public boolean login(String email, String password) {
        String storedEmail = sharedPreferences.getString(KEY_EMAIL, null);
        String storedPassword = sharedPreferences.getString(KEY_PASSWORD, null);
        if (email.equals(storedEmail) && password.equals(storedPassword)) {
            sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply();
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
    }

    public void deleteAccount() {
        sharedPreferences.edit().clear().apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public boolean hasSetPreferences() {
        return sharedPreferences.getBoolean(KEY_HAS_PREFERENCES, false);
    }

    public void savePreferences(Set<String> dietary, Set<String> allergens) {
        sharedPreferences.edit()
                .putStringSet(KEY_DIETARY, dietary)
                .putStringSet(KEY_ALLERGENS, allergens)
                .putBoolean(KEY_HAS_PREFERENCES, true)
                .apply();
    }

    public Set<String> getDietaryPreferences() {
        return sharedPreferences.getStringSet(KEY_DIETARY, new HashSet<>());
    }

    public Set<String> getAllergenPreferences() {
        return sharedPreferences.getStringSet(KEY_ALLERGENS, new HashSet<>());
    }

    public void saveRecipe(String recipeId) {
        Set<String> saved = new HashSet<>(sharedPreferences.getStringSet(KEY_SAVED_RECIPES, new HashSet<>()));
        saved.add(recipeId);
        sharedPreferences.edit().putStringSet(KEY_SAVED_RECIPES, saved).apply();
    }

    public void dislikeRecipe(String recipeId) {
        Set<String> disliked = new HashSet<>(sharedPreferences.getStringSet(KEY_DISLIKED_RECIPES, new HashSet<>()));
        disliked.add(recipeId);
        sharedPreferences.edit().putStringSet(KEY_DISLIKED_RECIPES, disliked).apply();
    }

    public Set<String> getSavedRecipes() {
        return sharedPreferences.getStringSet(KEY_SAVED_RECIPES, new HashSet<>());
    }

    public Set<String> getDislikedRecipes() {
        return sharedPreferences.getStringSet(KEY_DISLIKED_RECIPES, new HashSet<>());
    }
}
