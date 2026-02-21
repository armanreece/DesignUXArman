/*
 * File: DiscoveryFragment.java
 * Author(s): Arman
 * Purpose: Display recipes one by one with swipe and button actions.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: RecipeViewModel, GestureDetector, RecipeDetailActivity
 */
package com.example.designuxarman;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.designuxarman.model.Recipe;
import com.example.designuxarman.viewmodel.RecipeViewModel;
import java.util.List;

public class DiscoveryFragment extends Fragment {

    private RecipeViewModel viewModel;
    private FrameLayout cardContainer;
    private TextView tvEmptyState;
    private List<Recipe> currentRecipes;
    private int currentIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        cardContainer = view.findViewById(R.id.card_container);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);

        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        viewModel.getDiscoveryRecipes().observe(getViewLifecycleOwner(), recipes -> {
            currentRecipes = recipes;
            currentIndex = 0;
            showNextRecipe();
        });

        return view;
    }

    private void showNextRecipe() {
        cardContainer.removeAllViews();
        if (currentRecipes == null || currentIndex >= currentRecipes.size()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        tvEmptyState.setVisibility(View.GONE);
        Recipe recipe = currentRecipes.get(currentIndex);
        View cardView = getLayoutInflater().inflate(R.layout.item_recipe_card, cardContainer, false);
        
        TextView title = cardView.findViewById(R.id.tv_recipe_title);
        TextView allergens = cardView.findViewById(R.id.tv_recipe_allergens);
        Button btnLike = cardView.findViewById(R.id.btn_like);
        Button btnDislike = cardView.findViewById(R.id.btn_dislike);
        Button btnDetails = cardView.findViewById(R.id.btn_details);

        title.setText(recipe.getTitle());
        allergens.setText("Allergens: " + String.join(", ", recipe.getAllergens()));

        btnLike.setOnClickListener(v -> handleLike(recipe));
        btnDislike.setOnClickListener(v -> handleDislike(recipe));
        btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });

        setupSwipeGesture(cardView, recipe);
        cardContainer.addView(cardView);
    }

    private void setupSwipeGesture(View view, Recipe recipe) {
        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                    if (diffX > 0) {
                        handleLike(recipe);
                    } else {
                        handleDislike(recipe);
                    }
                    return true;
                }
                return false;
            }
        });

        view.setOnTouchListener((v, event) -> {
            v.performClick();
            return gestureDetector.onTouchEvent(event);
        });
    }

    private void handleLike(Recipe recipe) {
        viewModel.likeRecipe(recipe);
        currentIndex++;
        showNextRecipe();
    }

    private void handleDislike(Recipe recipe) {
        viewModel.dislikeRecipe(recipe);
        currentIndex++;
        showNextRecipe();
    }
}
