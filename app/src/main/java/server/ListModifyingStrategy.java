package server;

import java.util.List;

public interface ListModifyingStrategy {
    // return new List with modified List
    public List<Recipe> getModifiedList(List<Recipe> recipes);
}
