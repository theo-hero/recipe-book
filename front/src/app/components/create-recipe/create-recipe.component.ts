import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecipeService } from 'src/recipe.service';
import { forkJoin } from 'rxjs';

interface Ingredient {
  name: string;
  customName?: string;
  quantity: number | null;
  unit: string;
}

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent {
  @Input() recipeToEdit: any = null;
  @Output() closeWindow = new EventEmitter<void>();
  @Output() createUpdateRecipe = new EventEmitter<any>();

  ingredientOptions: string[] = [];
  unitOptions: string[] = [];

  recipe = {
    id: null,
    title: '',
    description: '',
    instruction: '',
    ingredients: [] as Ingredient[],
    img_url: ''
  };

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
    const fetchUnitOptions$ = this.recipeService.getUnits();
    const fetchIngredientOptions$ = this.recipeService.getIngredients();

    forkJoin([fetchUnitOptions$, fetchIngredientOptions$]).subscribe({
      next: ([unitResponse, ingredientResponse]) => {
        this.unitOptions = unitResponse;
        this.ingredientOptions = ingredientResponse;

        if (this.recipeToEdit) {
          this.recipe = {
            ...this.recipeToEdit,
            ingredients: this.parseIngredients(this.recipeToEdit.ingredients)
          };
        }
      },
      error: (error: any) => {
        console.error('Error fetching options:', error);
      }
    });
  }

  addIngredient(): void {
    this.recipe.ingredients.push({ name: '', customName: '', quantity: null, unit: '' });
  }

  removeIngredient(index: number): void {
    if (index >= 0 && index < this.recipe.ingredients.length) {
      this.recipe.ingredients.splice(index, 1);
    }
  }

  closeTheWindow(event: MouseEvent): void {
    event.stopPropagation();
    this.closeWindow.emit();
  }

  parseIngredients(ingredientsString: string): any[] {
    return ingredientsString.split(':').map(ingredient => {
      const [name, quantity, unit] = ingredient.split('*');
      const parsedIngredient = {
        name: name.trim(),
        quantity: parseFloat(quantity),
        unit: unit.trim(),
        customName: ""
      };

      if (!this.ingredientOptions.includes(parsedIngredient.name)) {
        parsedIngredient.name = 'custom';
        parsedIngredient.customName = name.trim();
      }

      if (!this.unitOptions.includes(parsedIngredient.unit)) {
        parsedIngredient.unit = '';
      }

      return parsedIngredient;
    });
  }

  onSubmit(): void {
    if (
      this.recipe.title.trim() === '' ||
      this.recipe.description.trim() === '' ||
      this.recipe.instruction.trim() === '' ||
      this.recipe.ingredients.some((ing) => !ing.name || ing.quantity === null || !ing.unit)
    ) {
      alert('Пожалуйста, заполните все необходимые поля');
      return;
    }

    this.createUpdateRecipe.emit(this.recipe);

    this.recipe = {
      id: null,
      title: '',
      description: '',
      instruction: '',
      ingredients: [],
      img_url: ''
    };

    this.closeWindow.emit();
  }
}
