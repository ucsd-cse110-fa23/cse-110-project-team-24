package server;

import java.util.List;
import java.util.ArrayList;


public class ChronologicalSorter implements ListModifyingStrategy{


    /**
     * Assumes input recipe list is already sorted in chronological order
     */
    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            result.add(i, recipes.get(i));
        }
        return result;
    }
    
}
