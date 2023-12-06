package server;

import java.util.ArrayList;
import java.util.List;

public class NoFilter implements ListModifyingStrategy{

    @Override
    public List<Recipe> getModifiedList(List<Recipe> recipes) {
        List<Recipe> result = new ArrayList<Recipe>();
        for (Recipe recipe: recipes) {
            result.add(result.size(), recipe); // add every recipe in same order
        }
        return result;
    }

    @Override
    public boolean isSortingStrategy() {
        return false;
    }
    
}