/*
 * File: RecipeDetailActivity.java
 * Author(s): Arman
 * Purpose: Display full details of a recipe and handle video intent.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: Recipe model, ACTION_VIEW intent
 */
package com.example.designuxarman;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.designuxarman.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        if (recipe == null) {
            finish();
            return;
        }

        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAllergens = findViewById(R.id.tv_detail_allergens);
        TextView tvIngredients = findViewById(R.id.tv_detail_ingredients);
        TextView tvSteps = findViewById(R.id.tv_detail_steps);
        Button btnVideo = findViewById(R.id.btn_watch_video);

        tvTitle.setText(recipe.getTitle());
        tvAllergens.setText(String.join(", ", recipe.getAllergens()));
        tvIngredients.setText(String.join("\n", recipe.getIngredients()));
        tvSteps.setText(String.join("\n", recipe.getSteps()));

        if (recipe.getVideoUrl() != null && !recipe.getVideoUrl().isEmpty()) {
            btnVideo.setVisibility(View.VISIBLE);
            btnVideo.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getVideoUrl()));
                startActivity(intent);
            });
        }
    }
}
