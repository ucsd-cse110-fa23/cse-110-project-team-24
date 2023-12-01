package server;

import java.util.List;
import java.util.ArrayList;

public class AlphabeticalSorter implements ListModifyingStrategy{

    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (Recipe recipe:recipes) {
            this.addInAlphabeticalPostition(result, recipe);
        }
        return result;
    }

    void addInAlphabeticalPostition(List<Recipe> recipes, Recipe recipe) {
        String title = recipe.getTitle().toLowerCase();
        for (int i = 0; i < recipes.size(); i++) {
            String currentRecipeTitle = recipes.get(i).getTitle().toLowerCase();
            int comparison = title.compareTo(currentRecipeTitle);
            if (comparison < 0) {
                recipes.add(i, recipe);
                return;
            }
        }
        recipes.add(recipes.size(), recipe);
    }
    
}
