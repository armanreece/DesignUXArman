/*
 * File: RecipeViewModel.java
 * Author(s): Arman
 * Purpose: ViewModel for managing recipe data and user interactions.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: ViewModel, RecipeRepository, UserRepository
 */
package com.example.designuxarman.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.designuxarman.model.Recipe;
import com.example.designuxarman.repository.RecipeRepository;
import com.example.designuxarman.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeViewModel extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<List<Recipe>> discoveryRecipes = new MutableLiveData<>();
    private final MutableLiveData<List<Recipe>> savedRecipes = new MutableLiveData<>();

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository();
        userRepository = new UserRepository(application);
        loadDiscoveryRecipes();
        loadSavedRecipes();
    }

    public void loadDiscoveryRecipes() {
        List<Recipe> all = recipeRepository.getAllRecipes();
        Set<String> savedIds = userRepository.getSavedRecipes();
        Set<String> dislikedIds = userRepository.getDislikedRecipes();
        
        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : all) {
            if (!savedIds.contains(r.getId()) && !dislikedIds.contains(r.getId())) {
                filtered.add(r);
            }
        }
        discoveryRecipes.setValue(filtered);
    }

    public void loadSavedRecipes() {
        Set<String> savedIds = userRepository.getSavedRecipes();
        List<Recipe> saved = new ArrayList<>();
        for (String id : savedIds) {
            Recipe r = recipeRepository.getRecipeById(id);
            if (r != null) saved.add(r);
        }
        savedRecipes.setValue(saved);
    }

    public LiveData<List<Recipe>> getDiscoveryRecipes() {
        return discoveryRecipes;
    }

    public LiveData<List<Recipe>> getSavedRecipes() {
        return savedRecipes;
    }

    public void likeRecipe(Recipe recipe) {
        userRepository.saveRecipe(recipe.getId());
        loadDiscoveryRecipes();
        loadSavedRecipes();
    }

    public void dislikeRecipe(Recipe recipe) {
        userRepository.dislikeRecipe(recipe.getId());
        loadDiscoveryRecipes();
    }
}
