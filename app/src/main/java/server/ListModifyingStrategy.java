package server;

import java.util.List;

public interface ListModifyingStrategy {
    // return sorted or filtered copy of param recipes
    public List<Recipe> getModifiedList(List<Recipe> recipes);

    // return true iff strategy sorts list (meaning strategy changes order but maintains recipes)
    public boolean isSortingStrategy();
}
