package server;

import java.util.List;
import java.util.ArrayList;

public class ReverseChronologicalSorter implements ListModifyingStrategy{

    /**
     * Assumes argument already sorted in chronological order
     */
    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            result.add(i, recipes.get(recipes.size() - 1 - i));
        }
        return result;
    }
    
}