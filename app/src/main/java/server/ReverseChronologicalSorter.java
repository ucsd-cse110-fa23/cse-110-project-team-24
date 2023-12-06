package server;

import java.util.List;
import java.util.ArrayList;
import java.time.ZonedDateTime;
public class ReverseChronologicalSorter implements ListModifyingStrategy{

    /**
     * Assumes argument already sorted in chronological order
     */
    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            result = this.insertInReverseChronologicalOrder(recipes.get(i), result); // add recipe in appropriate position
        }
        return result;
    }

    // add param r to param recipes while maintining reverse-chronological order of recipes
    List<Recipe> insertInReverseChronologicalOrder(Recipe r, List<Recipe> recipes) {
        ZonedDateTime recipeDateTime = ZonedDateTime.parse(r.getDate());
        for (int i = 0; i < recipes.size(); i++) {
            ZonedDateTime currentDateTime = ZonedDateTime.parse(recipes.get(i).getDate()); 
            int comparison = recipeDateTime.compareTo(currentDateTime); // compare dateTime of each recipe
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