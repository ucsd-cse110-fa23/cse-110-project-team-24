package server;

import java.util.List;

import pantrypal.RecipeView;

import java.util.ArrayList;
import java.time.ZonedDateTime;


public class ChronologicalSorter implements ListModifyingStrategy{


    /**
     * Assumes input recipe list is already sorted in chronological order
     */
    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            result = this.insertInChronologicalOrder(recipes.get(i), result);
        }
        return result;
    }

    List<Recipe> insertInChronologicalOrder(Recipe r, List<Recipe> recipes) {
        ZonedDateTime recipeDateTime = ZonedDateTime.parse(r.getDate());
        for (int i = 0; i < recipes.size(); i++) {
            ZonedDateTime currentDateTime = ZonedDateTime.parse(recipes.get(i).getDate());
            int comparison = -recipeDateTime.compareTo(currentDateTime);
            if (comparison < 0) {
                recipes.add(i, r);
                return recipes;
            }
        }
        recipes.add(recipes.size(), r);
        return recipes;
    }

    @Override
    public boolean isSortingStrategy() {
        return true;
    }
    
}
