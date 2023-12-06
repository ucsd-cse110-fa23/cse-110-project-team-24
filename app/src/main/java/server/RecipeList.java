package server;

import java.util.List;
import java.util.ArrayList;

// RecipeList class manages a list of recipes with sorting and filtering strategies
public class RecipeList {

    List<Recipe> recipes;
    ListModifyingStrategy sortStrategy; 
    ListModifyingStrategy filterStrategy;
    List<Recipe> modifiedRecipes; // up to date modified list (according to sorting and filter strategies)

    RecipeList () {
        this.sortStrategy = new ChronologicalSorter();
        this.filterStrategy = new NoFilter();
        this.recipes = new ArrayList<Recipe>();
        modifiedRecipes = new ArrayList<Recipe>();
    }
    // Constructor with custom sorting and filtering strategies
    RecipeList (ListModifyingStrategy sortStrategy, ListModifyingStrategy filterStrategy) {
        this.sortStrategy = sortStrategy;
        this.filterStrategy = filterStrategy;
        this.recipes = new ArrayList<Recipe>();
        modifiedRecipes = new ArrayList<Recipe>();
    }

    // Set a new sorting or filtering strategy
    public void setListModifyingStrategy(ListModifyingStrategy newStrategy) {
        if (newStrategy.isSortingStrategy()) {
            this.sortStrategy = newStrategy;
        }
        else {
            this.filterStrategy = newStrategy;
        }

        this.updateModifiedRecipes();
    }

     // Update the modified list according to the current sorting and filtering strategies
    private void updateModifiedRecipes() {
        this.modifiedRecipes = sortStrategy.getModifiedList(recipes);
        this.modifiedRecipes = filterStrategy.getModifiedList(modifiedRecipes);
    }
     // Add a recipe to the list and update the modified list
    public void add (Recipe r) {
        this.recipes.add(0, r);
        this.updateModifiedRecipes();
    }
     // Add a recipe at a specific index and update the modified list
    public void add (int i, Recipe r) {
        this.recipes.add(i, r);
        this.updateModifiedRecipes();
    }
    // Add a list of recipes to the current list and update the modified list
    public void addAll(List<Recipe> recipesToAdd) {
        this.recipes.addAll(recipesToAdd);
        this.updateModifiedRecipes();
    }
    // Get the current modified list of recipes
    public List<Recipe> getModifiedRecipes() {
        return this.modifiedRecipes;
    }

    // return unsorted and unfiltered list
    public List<Recipe> getList() {
        return this.recipes;
    }

    // get recipe from sorted and filtered list
    public Recipe get(int i) {
        return this.modifiedRecipes.get(i);
    }

    // get sorted and filtered list size
    public int size() {
        return this.modifiedRecipes.size();
    }

   
    public void remove(Recipe recipe) {
        this.recipes.remove(recipe);
        this.updateModifiedRecipes();
    }

    public void removeAll() {
        while (this.recipes.size() > 0) {
            this.recipes.remove(this.recipes.size() -1);
        }
        this.updateModifiedRecipes();
    }

    // Set a sorting or filtering strategy based on a string query
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
            case "NoFilter":
                this.setListModifyingStrategy(new NoFilter());
                break;
            case "Breakfast":
                this.setListModifyingStrategy(new BreakfastFilter());
                break;
            case "Lunch":
                this.setListModifyingStrategy(new LunchFilter());
                break;
            case "Dinner":
                this.setListModifyingStrategy(new DinnerFilter());
                break;
        }
    }
}
