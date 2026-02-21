/*
 * File: SavedRecipeAdapter.java
 * Author(s): Arman
 * Purpose: Adapter for the grid of saved recipes in CookbookFragment.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: RecyclerView, Recipe model
 */
package com.example.designuxarman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.designuxarman.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private final OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public SavedRecipeAdapter(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_saved_recipe_image);
            tvTitle = itemView.findViewById(R.id.tv_saved_recipe_title);
        }
    }
}
