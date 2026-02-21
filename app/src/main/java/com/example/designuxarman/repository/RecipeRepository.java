/*
 * File: RecipeRepository.java
 * Author(s): Arman
 * Purpose: Repository for recipe data (in-memory for now).
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: Recipe model
 */
package com.example.designuxarman.repository;

import com.example.designuxarman.model.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {
    private final List<Recipe> recipes;

    public RecipeRepository() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe("1", "Vegan Pasta", Arrays.asList("None"), 
                Arrays.asList("Pasta", "Tomato Sauce", "Basil"), 
                Arrays.asList("Boil pasta", "Heat sauce", "Mix and serve"), 
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://via.placeholder.com/300"));
        recipes.add(new Recipe("2", "Nutty Salad", Arrays.asList("Nuts"), 
                Arrays.asList("Lettuce", "Walnuts", "Vinaigrette"), 
                Arrays.asList("Chop lettuce", "Add nuts", "Drizzle dressing"), 
                null, "https://via.placeholder.com/300"));
        recipes.add(new Recipe("3", "Cheesy Omelette", Arrays.asList("Dairy"), 
                Arrays.asList("Eggs", "Cheese", "Butter"), 
                Arrays.asList("Whisk eggs", "Melt butter", "Cook with cheese"), 
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://via.placeholder.com/300"));
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public Recipe getRecipeById(String id) {
        for (Recipe r : recipes) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }
}
