package com.example.recipe_book;

import com.example.recipe_book.model.Ingredient;
import com.example.recipe_book.model.Recipe;
import com.example.recipe_book.model.RecipeView;
import com.example.recipe_book.model.Tag;
import com.example.recipe_book.model.Unit;

//import com.example.recipe_book.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Map;
import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<RecipeView> getRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeView getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/units")
    public List<String> getAllUnits() {
        return recipeService.getAllUnits();
    }

    @GetMapping("/tags")
    public List<String> getAllTags() {
        return recipeService.getAllTags();
    }

    @GetMapping("/ingredients")
    public List<String> getAllIngredients() {
        return recipeService.getAllIngredients();
    }

    @PostMapping("/units")
    public Unit createUnit(@RequestBody Unit unitRequest) {
        return recipeService.createUnit(unitRequest.getName());
    }

    @PostMapping("/tags")
    public Tag createTag(@RequestBody Tag tagRequest) {
        return recipeService.createTag(tagRequest.getName());
    }

    @PostMapping("/ingredients")
    public Ingredient createIngredient(@RequestBody Ingredient ingRequest) {
        return recipeService.createIngredient(ingRequest.getName());
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeDTO recipeDto) {
        try {
            Recipe createdRecipe = recipeService.createRecipe(recipeDto);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Recipe created successfully");
            responseBody.put("recipeId", createdRecipe.getId());
            responseBody.put("title", createdRecipe.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create recipe: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
    //     try {
    //         Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDTO);
    //         return ResponseEntity.ok(updatedRecipe);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    // }
}