package server;

import java.util.ArrayList;
import java.util.List;

public class DinnerFilter implements ListModifyingStrategy{

    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (Recipe recipe: recipes) {
            if (recipe.getMealType().equals(Recipe.DINNER)) {
                result.add(result.size(), recipe); // add recipe if mealtype is dinner
            }
        }
        return result;
    }

    @Override
    public boolean isSortingStrategy() {
        return false;
    }
    
}
