/*
 * File: Recipe.java
 * Author(s): Arman
 * Purpose: Data model for a recipe.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: None
 */
package com.example.designuxarman.model;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private final String id;
    private final String title;
    private final List<String> allergens;
    private final List<String> ingredients;
    private final List<String> steps;
    private final String videoUrl;
    private final String imageUrl;

    public Recipe(String id, String title, List<String> allergens, List<String> ingredients, List<String> steps, String videoUrl, String imageUrl) {
        this.id = id;
        this.title = title;
        this.allergens = allergens;
        this.ingredients = ingredients;
        this.steps = steps;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public List<String> getAllergens() { return allergens; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getSteps() { return steps; }
    public String getVideoUrl() { return videoUrl; }
    public String getImageUrl() { return imageUrl; }
}
