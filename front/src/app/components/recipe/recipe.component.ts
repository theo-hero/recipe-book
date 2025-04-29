import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent {
  @Input() recipe!: { id: number, title: string, description: string, dateCreated: string };
  @Output() clickEvent = new EventEmitter<number>();

  onClick() {
    this.clickEvent.emit(this.recipe.id);
  }
}
