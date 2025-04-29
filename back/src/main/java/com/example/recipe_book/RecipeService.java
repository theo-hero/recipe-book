package com.example.recipe_book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe_book.model.Ingredient;
import com.example.recipe_book.model.Recipe;
import com.example.recipe_book.model.RecipeView;
import com.example.recipe_book.model.Tag;
import com.example.recipe_book.model.Unit;
import com.example.recipe_book.repository.IngredientRepository;
import com.example.recipe_book.repository.TagRepository;
import com.example.recipe_book.repository.UnitRepository;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    // @Autowired
    // private RecipeIngredientsRepository recipeIngredientsRepository;

    public List<RecipeView> getAllRecipes() {
        List<RecipeView> rows = recipeRepository.findRecipes();
        return rows;
    }

    public RecipeView getRecipeById(Long id) {
        RecipeView recipe = recipeRepository.findRecipeDetailsById(id);
        return recipe;
    }

    public List<String> getAllTags() {
        return recipeRepository.findAllTagNames();
    }

    public List<String> getAllIngredients() {
        return recipeRepository.findAllIngredientNames();
    }

    public List<String> getAllUnits() {
        return recipeRepository.findAllUnitNames();
    }

    public Unit createUnit(String name) {
        Unit unit = new Unit();
        unit.setName(name);
        return unitRepository.save(unit);
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    public Ingredient createIngredient(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        return ingredientRepository.save(ingredient);
    }

    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setInstruction(recipeDTO.getInstruction());
        recipe.setImageURL(recipeDTO.getImgUrl());
        recipe.setDateCreated(LocalDate.now());

        List<RecipeIngredients> recipeIngredients = new ArrayList<>();
        for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientDTO.getIngredientName())
                    .orElseGet(() -> createIngredient(ingredientDTO.getIngredientName()));

            Unit unit = unitRepository.findByName(ingredientDTO.getUnitName())
                    .orElseThrow(() -> new RuntimeException("Unit not found: " + ingredientDTO.getUnitName()));

            RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
            recipeIngredientId.setRecipeId(recipe.getId());
            recipeIngredientId.setIngredientId(ingredient.getId());

            RecipeIngredients ri = new RecipeIngredients();
            ri.setId(recipeIngredientId);
            ri.setQuantity(ingredientDTO.getQuantity());
            ri.setIngredient(ingredient);
            ri.setUnit(unit);
            ri.setRecipe(recipe);

            recipeIngredients.add(ri);
        }
        recipe.setIngredients(recipeIngredients);

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found: " + id);
        }
        recipeRepository.deleteById(id);
    }

    // private List<RecipeIngredientDTO> parseIngredientsString(String ingredientsString) {
    //     List<RecipeIngredientDTO> ingredients = new ArrayList<>();
    //     if (ingredientsString != null && !ingredientsString.isEmpty()) {
    //         for (String ingredient : ingredientsString.split(":")) {
    //             String[] parts = ingredient.split("\\*");
    //             if (parts.length == 3) {
    //                 RecipeIngredientDTO dto = new RecipeIngredientDTO();
    //                 dto.setIngredientName(parts[0]);
    //                 dto.setQuantity(Double.parseDouble(parts[1]));
    //                 dto.setUnitName(parts[2]);
    //                 ingredients.add(dto);
    //             }
    //         }
    //     }
    //     return ingredients;
    // }

    // private String constructIngredientsString(List<RecipeIngredients> ingredients) {
    //     return ingredients.stream()
    //             .map(ri -> ri.getIngredient().getName() + "*" + ri.getQuantity() + "*" + ri.getUnit().getName())
    //             .collect(Collectors.joining(":"));
    // }

    // public Recipe updateRecipe(Long id, RecipeDTO recipeDTO) {
    //     RecipeView recipeView = recipeRepository.findRecipeById(id)
    //             .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));

    //     Recipe existingRecipe = new Recipe();
    //     existingRecipe.setId(recipeView.getId());
    //     existingRecipe.setTitle(recipeDTO.getTitle() != null ? recipeDTO.getTitle() : recipeView.getTitle());
    //     existingRecipe.setDescription(
    //             recipeDTO.getDescription() != null ? recipeDTO.getDescription() : recipeView.getDescription());
    //     existingRecipe.setInstruction(
    //             recipeDTO.getInstruction() != null ? recipeDTO.getInstruction() : recipeView.getInstruction());
    //     existingRecipe.setImageURL(recipeDTO.getImgUrl() != null ? recipeDTO.getImgUrl() : recipeView.getImageURL());

    //     List<RecipeIngredientDTO> existingIngredients = parseIngredientsString(recipeView.getIngredients());

    //     List<RecipeIngredients> updatedIngredients = new ArrayList<>();
    //     for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
    //         Ingredient ingredient = ingredientRepository.findByName(ingredientDTO.getIngredientName())
    //                 .orElseGet(() -> createIngredient(ingredientDTO.getIngredientName()));

    //         Unit unit = unitRepository.findByName(ingredientDTO.getUnitName())
    //                 .orElseThrow(() -> new RuntimeException("Unit not found: " + ingredientDTO.getUnitName()));

    //         RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
    //         recipeIngredientId.setRecipeId(existingRecipe.getId());
    //         recipeIngredientId.setIngredientId(ingredient.getId());

    //         RecipeIngredients ri = recipeIngredientsRepository.findRecipeIngredientById(
    //                 recipeIngredientId.getRecipeId(),
    //                 recipeIngredientId.getIngredientId()).orElse(new RecipeIngredients());

    //         ri.setId(recipeIngredientId);
    //         ri.setQuantity(ingredientDTO.getQuantity());
    //         ri.setIngredient(ingredient);
    //         ri.setUnit(unit);
    //         ri.setRecipe(existingRecipe);

    //         if (!updatedIngredients.stream()
    //                 .anyMatch(existing -> existing.getId().equals(recipeIngredientId))) {
    //             updatedIngredients.add(ri);
    //         }
    //     }

    //     for (RecipeIngredientDTO existingIngredient : existingIngredients) {
    //         boolean isPresent = recipeDTO.getIngredients().stream()
    //                 .anyMatch(dto -> dto.getIngredientName().equals(existingIngredient.getIngredientName())
    //                         && dto.getUnitName().equals(existingIngredient.getUnitName())
    //                         && dto.getQuantity() == existingIngredient.getQuantity());

    //         if (!isPresent) {
    //             RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
    //             recipeIngredientId.setRecipeId(existingRecipe.getId());
    //             recipeIngredientId.setIngredientId(
    //                     ingredientRepository.findByName(existingIngredient.getIngredientName()).get().getId());
    //             recipeIngredientsRepository.deleteRecipeIngredientById(
    //                     recipeIngredientId.getRecipeId(),
    //                     recipeIngredientId.getIngredientId());
    //         }
    //     }

    //     existingRecipe.setIngredients(updatedIngredients);
    //     Recipe savedRecipe = recipeRepository.save(existingRecipe);
    //     String updatedIngredientsString = constructIngredientsString(savedRecipe.getIngredients());

    //     return savedRecipe;
    // }
}
