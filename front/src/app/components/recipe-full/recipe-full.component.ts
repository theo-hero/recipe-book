import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { RecipeService } from 'src/recipe.service';

@Component({
  selector: 'app-recipe-full',
  templateUrl: './recipe-full.component.html',
  styleUrls: ['./recipe-full.component.css']
})
export class RecipeFullComponent {
  @Input() recipeId: number | null = null;
  @Output() closeWindow = new EventEmitter<void>();
  @Output() deleteEntry = new EventEmitter<number>();
  @Output() editRecipeEvent = new EventEmitter<number>();
  recipe: any = null;

  ngOnInit(): void {
    if (this.recipeId !== null) {
      this.recipeService.getRecipeById(this.recipeId).subscribe(data => {
        this.recipe = data;
        if (this.recipe && this.recipe.ingredients) {
          this.recipe.ingredients = this.parseIngredients(this.recipe.ingredients);
        }
        //console.log(this.recipe);
      });
    } else {
      this.recipe = null;
    }
  }

  parseIngredients(ingredientsString: string): any[] {
    return ingredientsString.split(':').map(ingredient => {
      const [ingredientName, quantity, unitName] = ingredient.split('*');
      return { ingredientName, quantity: parseFloat(quantity), unitName };
    });
  }

  constructor(private recipeService: RecipeService) { }

  closeTheWindow(event: MouseEvent) {
    event.stopPropagation();
    this.closeWindow.emit();
  }

  showOptionsMenu = false;

  toggleOptionsMenu(event: MouseEvent): void {
    event.stopPropagation();
    this.showOptionsMenu = !this.showOptionsMenu;
  }

  editRecipe(): void {
    if (this.recipe) {
      this.editRecipeEvent.emit(this.recipe.id);
    } else {
      console.error('Cannot edit recipe: recipe is null');
    }
    this.showOptionsMenu = false;
  }

  deleteRecipe(): void {
    if (this.recipeId !== null) {
      const confirmed = confirm('Вы уверены, что хотите удалить этот рецепт? Это действие нельзя отменить.');
      if (confirmed) this.deleteEntry.emit(this.recipeId);
    } else {
      console.error('Cannot delete recipe: recipeId is null');
    }
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent): void {
    this.showOptionsMenu = false;
  }
}
