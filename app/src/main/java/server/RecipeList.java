package server;

import java.util.List;
import java.util.ArrayList;


public class RecipeList {

    List<Recipe> recipes;
    ListModifyingStrategy lms;

    RecipeList (ListModifyingStrategy lms) {
        this.lms = lms;
        this.recipes = new ArrayList<Recipe>();
    }

    public void setListModifyingStrategy(ListModifyingStrategy newStrategy) {
        lms = newStrategy;
    }

    
    public void add (Recipe r) {
        this.recipes.add(0, r);
    }


    public List<Recipe> getModifiedRecipes(ListModifyingStrategy lms) {
        return lms.getModifiedList(this.recipes);
    }

    public List<Recipe> getList() {
        return this.recipes;
    }

    public int size() {
        return this.recipes.size();
    }

    public Recipe get(int i) {
        return this.recipes.get(i);
    }

    public void remove(Recipe recipe) {
        this.recipes.remove(recipe);
    }

    public void addAll(List<Recipe> recipesToAdd) {
        this.recipes.addAll(recipesToAdd);
    }
}
