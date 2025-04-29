import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { RecipeComponent } from './components/recipe/recipe.component';
import { RecipesListComponent } from './components/recipes-list/recipes-list.component';
import { RecipeFullComponent } from './components/recipe-full/recipe-full.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateRecipeComponent } from './components/create-recipe/create-recipe.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RecipeComponent,
    RecipesListComponent,
    RecipeFullComponent,
    CreateRecipeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
