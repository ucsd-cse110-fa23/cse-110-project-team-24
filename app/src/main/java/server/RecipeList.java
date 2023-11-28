package server;

import java.util.List;
import java.util.ArrayList;


public class RecipeList {

    List<Recipe> recipes;
    ListModifyingStrategy lms;
    List<Recipe> sortedRecipes; // up to date modified list (according to lms)

    RecipeList (ListModifyingStrategy lms) {
        this.lms = lms;
        this.recipes = new ArrayList<Recipe>();
        sortedRecipes = new ArrayList<Recipe>();
    }

    public void setListModifyingStrategy(ListModifyingStrategy newStrategy) {
        lms = newStrategy;
        sortedRecipes = lms.getModifiedList(recipes);
    }

    
    public void add (Recipe r) {
        this.recipes.add(0, r);
        sortedRecipes = lms.getModifiedList(recipes);
    }

    public void add (int i, Recipe r) {
        this.recipes.add(i, r);
        sortedRecipes = lms.getModifiedList(recipes);
    }


    public List<Recipe> getModifiedRecipes(ListModifyingStrategy lms) {
        return this.sortedRecipes;
    }

    public List<Recipe> getList() {
        return this.recipes;
    }

    public int size() {
        return this.sortedRecipes.size();
    }

    public Recipe get(int i) {
        return this.sortedRecipes.get(i);
    }

    public void remove(Recipe recipe) {
        this.recipes.remove(recipe);
        this.updateSortedRecipes();
    }

    private void updateSortedRecipes() {
        sortedRecipes = lms.getModifiedList(this.recipes);
    }

    public void addAll(List<Recipe> recipesToAdd) {
        this.recipes.addAll(recipesToAdd);
        this.sortedRecipes = lms.getModifiedList(recipes);
    }

    public void setListModifyingStrategy(String query) {
        switch (query) {
            case "Alphabetical":
                this.setListModifyingStrategy(new AlphabeticalSorter());
                break;
            case "Chronological":
                this.setListModifyingStrategy(new ChronologicalSorter());
                break;
            case "ReverseChronological":
                this.setListModifyingStrategy(new ReverseChronologicalSorter());
                break;
            case "ReverseAlphabetical":
                this.setListModifyingStrategy(new ReverseAlphabeticalSorter());
                break;
        }
    }
}
