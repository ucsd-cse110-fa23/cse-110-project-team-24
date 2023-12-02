package server;

import java.util.List;
import java.util.ArrayList;


public class RecipeList {

    List<Recipe> recipes;
    ListModifyingStrategy sortStrategy;
    ListModifyingStrategy filterStrategy;
    List<Recipe> modifiedRecipes; // up to date modified list (according to lms)



    RecipeList () {
        this.sortStrategy = new ChronologicalSorter();
        this.filterStrategy = new NoFilter();
        this.recipes = new ArrayList<Recipe>();
        modifiedRecipes = new ArrayList<Recipe>();
    }
    RecipeList (ListModifyingStrategy sortStrategy, ListModifyingStrategy filterStrategy) {
        this.sortStrategy = sortStrategy;
        this.filterStrategy = filterStrategy;
        this.recipes = new ArrayList<Recipe>();
        modifiedRecipes = new ArrayList<Recipe>();
    }

    public void setListModifyingStrategy(ListModifyingStrategy newStrategy) {
        if (newStrategy.isSortingStrategy()) {
            this.sortStrategy = newStrategy;
        }
        else {
            this.filterStrategy = newStrategy;
        }

        this.updateModifiedRecipes();
    }

    
    private void updateModifiedRecipes() {
        this.modifiedRecipes = sortStrategy.getModifiedList(recipes);
        this.modifiedRecipes = filterStrategy.getModifiedList(modifiedRecipes);
    }

    public void add (Recipe r) {
        this.recipes.add(0, r);
        this.updateModifiedRecipes();
    }

    public void add (int i, Recipe r) {
        this.recipes.add(i, r);
        this.updateModifiedRecipes();
    }


    public List<Recipe> getModifiedRecipes() {
        return this.modifiedRecipes;
    }

    public List<Recipe> getList() {
        return this.recipes;
    }

    public int size() {
        return this.modifiedRecipes.size();
    }

    public Recipe get(int i) {
        return this.modifiedRecipes.get(i);
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

    public void addAll(List<Recipe> recipesToAdd) {
        this.recipes.addAll(recipesToAdd);
        this.updateModifiedRecipes();
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
