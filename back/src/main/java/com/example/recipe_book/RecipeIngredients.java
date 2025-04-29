package com.example.recipe_book;

import com.example.recipe_book.model.Ingredient;
import com.example.recipe_book.model.Recipe;
import com.example.recipe_book.model.Unit;

import jakarta.persistence.*;

@Entity
public class RecipeIngredients {
    @EmbeddedId
    private RecipeIngredientId id;

    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;

    public RecipeIngredientId getId() {
        return id;
    }

    public void setId(RecipeIngredientId id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
