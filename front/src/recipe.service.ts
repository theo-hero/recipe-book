import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './environments/environment';

@Injectable({
  providedIn: 'root'
})

export class RecipeService {
  private apiURL = "/api/recipes";

  constructor(private http: HttpClient) {}

  getRecipes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiURL);
  }

  getRecipeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/${id}`);
  }

  getUnits(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/units`);
  }

  getIngredients(): Observable<any> {
    return this.http.get<any>(`${this.apiURL}/ingredients`);
  }

  postRecipe(recipeDTO: any): Observable<any> {
    return this.http.post(`${this.apiURL}`, recipeDTO);
  }

  deleteRecipe(id: number): Observable<any[]> {
    return this.http.delete<any>(`${this.apiURL}/${id}`);
  }

  updateRecipe(id:number, recipeDTO: any): Observable<any> {
    return this.http.put(`${this.apiURL}/${id}`, recipeDTO);
  }
}
