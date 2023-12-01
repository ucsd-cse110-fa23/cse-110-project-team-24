package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


public class MealTypeTest {
    static final String BREAKFAST = "Breakfast";
    static final String LUNCH = "Lunch";
    static final String DINNER = "Dinner";

    @Test
    public void testGetMealType () {

        assertEquals(BREAKFAST, Recipe.getMealType(BREAKFAST));
        assertEquals(LUNCH, Recipe.getMealType(LUNCH));
        assertEquals(DINNER, Recipe.getMealType(DINNER));

        // punctuation
        assertEquals(BREAKFAST, Recipe.getMealType("Breakfast."));
        assertEquals(LUNCH, Recipe.getMealType("Lunch."));
        assertEquals(DINNER, Recipe.getMealType("Dinner."));

        // lowercase
        assertEquals(BREAKFAST, Recipe.getMealType("breakfast"));
        assertEquals(LUNCH, Recipe.getMealType("lunch"));
        assertEquals(DINNER, Recipe.getMealType("dinner"));

        // uppercase
        assertEquals(BREAKFAST, Recipe.getMealType("BREAKFAST"));
        assertEquals(LUNCH, Recipe.getMealType("LUNCH"));
        assertEquals(DINNER, Recipe.getMealType("DINNER"));

        // similar-sounding word
        assertEquals(BREAKFAST, Recipe.getMealType("breakast"));
        assertEquals(LUNCH, Recipe.getMealType("hunch"));
        assertEquals(DINNER, Recipe.getMealType("dilner"));

        // non-identifiable value
        assertEquals(DINNER, Recipe.getMealType("Something"));
    }




}