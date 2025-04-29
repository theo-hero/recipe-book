package com.example.recipe_book;

import java.time.LocalDate;
import java.util.List;

public class RecipeDTO {
    private String title;
    private String description;
    private String instruction;
    private String img_url;
    private List<RecipeIngredientDTO> ingredients;
    private LocalDate dateCreated;
    // private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img_url) {
        this.img_url = img_url;
    }

    public List<RecipeIngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    // public List<String> getTags() {
    //     return tags;
    // }

    // public void setTags(List<String> tags) {
    //     this.tags = tags;
    // }
}

