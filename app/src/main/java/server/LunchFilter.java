package server;

import java.util.ArrayList;
import java.util.List;

public class LunchFilter implements ListModifyingStrategy{

    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (Recipe recipe: recipes) {
            if (recipe.getMealType().equals(Recipe.LUNCH)) {
                result.add(result.size(), recipe); // add recipe if meal type is lunch
            }
        }
        return result;
    }

    @Override
    public boolean isSortingStrategy() {
        return false;
    }
    
}