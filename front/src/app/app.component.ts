import { Component } from '@angular/core';
import { RecipeService } from 'src/recipe.service';

interface Recipe {
  id: number;
  title: string;
  description: string;
  instruction: string;
  img_url: string;
  ingredients: any;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  recipeWindowIsVisible = false;
  createRecipeWindowIsVisible = false;
  selectedRecipeId: number | null = null;
  recipeToEdit: any;
  recipes: any[] = [];
  loading = true;
  errorWhileGettingRecipes = false;

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.loadRecipes();
  }

  loadRecipes() {
    this.recipeService.getRecipes().subscribe(data => {
      this.recipes = data;
      this.loading = false;
    }, error => {
      console.error('Error fetching recipes:', error);
      this.errorWhileGettingRecipes = true;
      this.loading = false;
    });
  }

  showFullRecipe(recipeId: number) {
    this.selectedRecipeId = recipeId;
    this.recipeWindowIsVisible = true;
  }

  closeFullRecipe() {
    this.recipeWindowIsVisible = false;
    this.selectedRecipeId = null;
  }

  showCreateRecipe() {
    this.createRecipeWindowIsVisible = true;
  }

  closeCreateRecipe() {
    this.createRecipeWindowIsVisible = false;
  }

  deleteEntry(recipeId: number) {
    this.recipeService.deleteRecipe(recipeId).subscribe(
      () => {
        console.log('Recipe deleted successfully.');
        this.recipes = this.recipes.filter((recipe) => recipe.id !== recipeId);
        if (this.selectedRecipeId === recipeId) {
          this.closeFullRecipe();
        }
      },
      (error) => {
        console.error('Error deleting recipe:', error);
      }
    );
  }

  createOrUpdateEntry(recipe: Recipe) {
    const recipeDTO = {
      id: recipe.id,
      title: recipe.title,
      description: recipe.description,
      instruction: recipe.instruction,
      img_url: recipe.img_url || '',
      ingredients: recipe.ingredients.map((ingredient: any) => ({
        quantity: ingredient.quantity,
        unitName: ingredient.unit,
        ingredientName: ingredient.name || ingredient.customName
      }))
    };
    if (recipe.id) this.updateEntry(recipe.id, recipeDTO);
    else this.createEntry(recipeDTO);
  }

  createEntry(recipeDTO: Recipe) {
    this.recipeService.postRecipe(recipeDTO).subscribe(
      (response) => {
        console.log('Recipe created successfully:', response);
        alert('Рецепт был сохранён!');
        this.loadRecipes();
      },
      (error) => console.error('Error creating recipe:', error)
    );

  }

  updateEntry(recipe_id: number, recipeDTO: Recipe) {
    this.recipeService.updateRecipe(recipe_id, recipeDTO).subscribe(
      (response) => {
        console.log('Recipe updated successfully:', response);
        alert('Рецепт был обновлён!');
      },
      (error) => console.error('Error updating recipe:', error)
    );
  }

  getRecipeById(id: number | null): any | null {
    if (!id) return null;
    return this.recipes.find((recipe) => recipe.id === id) || null;
  }

  editRecipe(recipeId: any): void {
    this.recipeToEdit = this.recipes.find((recipe) => recipe.id === recipeId);
    this.showCreateRecipe();
  }
}
