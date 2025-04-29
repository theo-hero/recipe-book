package com.example.recipe_book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.recipe_book.model.Recipe;
import com.example.recipe_book.model.RecipeView;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(value = "SELECT name FROM Tag", nativeQuery = true)
    List<String> findAllTagNames();

    @Query(value = "SELECT name FROM Ingredient", nativeQuery = true)
    List<String> findAllIngredientNames();

    @Query(value = "SELECT name FROM Unit", nativeQuery = true)
    List<String> findAllUnitNames();

    @Query(value = "SELECT * FROM recipe_view rv", nativeQuery = true)
    List<RecipeView> findAllRecipeDetails();

    @Query(value = "SELECT * FROM recipe_view rv WHERE rv.id = :recipeId", nativeQuery = true)
    RecipeView findRecipeDetailsById(@Param("recipeId") Long recipeId);

    @Query(value = "SELECT " +
            "r.id, " +
            "r.title, " +
            "r.description, " +
            "r.instruction, " +
            "r.img_url, " +
            "r.date_created, " +
            "COALESCE(string_agg(DISTINCT i.name || '*' || ri.quantity || '*' || u.name, ':'), '') AS ingredients, " +
            "COALESCE(string_agg(DISTINCT t.name, '*'), '') AS tags " +
            "FROM " +
            "Recipe r " +
            "LEFT JOIN " +
            "Recipe_ingredients ri ON r.id = ri.recipe_id " +
            "LEFT JOIN " +
            "Ingredient i ON ri.ingredient_id = i.id " +
            "LEFT JOIN " +
            "Unit u ON ri.unit_id = u.id " +
            "LEFT JOIN " +
            "Recipe_tags rt ON r.id = rt.recipe_id " +
            "LEFT JOIN " +
            "Tag t ON rt.tag_id = t.id " +
            "GROUP BY " +
            "r.id, r.title, r.description, r.instruction, r.img_url", nativeQuery = true)
    List<RecipeView> findRecipes();

    @Query(value = "SELECT " +
            "r.id, " +
            "r.title, " +
            "r.description, " +
            "r.instruction, " +
            "r.img_url, " +
            "r.date_created, " +
            "COALESCE(string_agg(DISTINCT i.name || '*' || ri.quantity || '*' || u.name, ':'), '') AS ingredients, " +
            "COALESCE(string_agg(DISTINCT t.name, '*'), '') AS tags " +
            "FROM " +
            "Recipe r " +
            "LEFT JOIN " +
            "Recipe_ingredients ri ON r.id = ri.recipe_id " +
            "LEFT JOIN " +
            "Ingredient i ON ri.ingredient_id = i.id " +
            "LEFT JOIN " +
            "Unit u ON ri.unit_id = u.id " +
            "LEFT JOIN " +
            "Recipe_tags rt ON r.id = rt.recipe_id " +
            "LEFT JOIN " +
            "Tag t ON rt.tag_id = t.id " +
            "WHERE r.id = :recipeId " +
            "GROUP BY " +
            "r.id, r.title, r.description, r.instruction, r.img_url", nativeQuery = true)
    Optional<RecipeView> findRecipeById(@Param("recipeId") Long recipeId);
}