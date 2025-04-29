import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecipeService } from '../../../recipe.service';

@Component({
  selector: 'app-recipes-list',
  templateUrl: './recipes-list.component.html',
  styleUrls: ['./recipes-list.component.css']
})
export class RecipesListComponent {
  @Input() recipes: any[] = [];
  @Output() recipeSelected = new EventEmitter<number>();

  constructor(private recipeService: RecipeService) {}

  onRecipeSelected(recipeId: number) {
    this.recipeSelected.emit(recipeId);
  }
}
