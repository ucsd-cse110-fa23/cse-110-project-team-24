package server;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import server.APIResponse;
import server.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AlphabeticalSortTest {

    @Test
    public void testAddInAlphabeticalPostitions() {
        Recipe r3 = new Recipe("B", null, null, null);
        Recipe r4 = new Recipe ("C", null, null, null);

        AlphabeticalSorter as = new AlphabeticalSorter();
        List<Recipe> addedRecipes = new ArrayList<Recipe>();
        as.addInAlphabeticalPostition(addedRecipes, r3);
        as.addInAlphabeticalPostition(addedRecipes, r4);

        assertEquals(r3, addedRecipes.get(0));
        assertEquals(r4, addedRecipes.get(1));

        Recipe r0 = new Recipe ("A", null, null, null);
        as.addInAlphabeticalPostition(addedRecipes, r0);
        assertEquals(r0, addedRecipes.get(0));

        Recipe r1 = new Recipe ("A", "Same title", null, null);
        as.addInAlphabeticalPostition(addedRecipes, r1);
        assertEquals(r1, addedRecipes.get(1));


        Recipe r2 = new Recipe ("ant", null, null, null);
        as.addInAlphabeticalPostition(addedRecipes, r2);
        assertEquals(r2, addedRecipes.get(2));

    }

    @Test
    public void testGetModifiedList() {
        AlphabeticalSorter as = new AlphabeticalSorter();
        List<Recipe> input = new ArrayList<>();

        assertEquals(0, as.getModifiedList(input).size());

        Recipe r3 = new Recipe("B", null, null, null);
        Recipe r4 = new Recipe ("C", null, null, null);
        Recipe r0 = new Recipe ("A", null, null, null);
        Recipe r1 = new Recipe ("A", "Same title", null, null);
        Recipe r2 = new Recipe ("ant", null, null, null);

        input.add(r3);
        input.add(r2);
        input.add(r0);
        input.add(r4);
        input.add(r1);

        List<Recipe> result = as.getModifiedList(input);

        assertEquals(r0, result.get(0));
        assertEquals(r1, result.get(1));
        assertEquals(r2, result.get(2));
        assertEquals(r3, result.get(3));
        assertEquals(r4, result.get(4));
    }
}
