/*
 * File: CookbookFragment.java
 * Author(s): Arman
 * Purpose: Displays a grid of saved recipes.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: RecipeViewModel, SavedRecipeAdapter, GridLayoutManager
 */
package com.example.designuxarman;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.designuxarman.viewmodel.RecipeViewModel;

public class CookbookFragment extends Fragment {

    private RecipeViewModel viewModel;
    private SavedRecipeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_saved_recipes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        adapter = new SavedRecipeAdapter(recipe -> {
            Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
        viewModel.getSavedRecipes().observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadSavedRecipes();
    }
}
