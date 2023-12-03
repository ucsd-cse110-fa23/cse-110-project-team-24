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

    @Test
    public void scenario1Test() {
    // Scenario 1: See Meal Types
	// Given that I am logged out and have created 1 “Breakfast” recipe named “Tofu Scramble” and 1 “Lunch” recipe named “Chickpea Sandwich”
	// When I login
	// Then I see two recipes in the list, with a “Breakfast” tag next to “Tofu Scramble” and a “Lunch” tag next to “Chickpea Sandwich”
	// Input: 	Recipe List with 2 recipes,
	// 	a breakfast called “Tofu Scramble”,
	// 	a lunch called “Chickpea Sandwich”
	// Expected Output: 
	// 	Tag “Breakfast” next to “Tofu Scramble”, 
	// 	Tag “Lunch” next to “Chickpea Sandwich”
        RecipeList rl = new RecipeList(new AlphabeticalSorter(), new NoFilter());
        Recipe r0 = new Recipe("Tofu Scramble", "BREAKFSAST.", null, null, "2020-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("Chickpea Sandwich", "lunch", null, null, "2020-12-03T10:15:30+01:00[Europe/Paris]");

        rl.add(r0);
        rl.add(r1);

        assertEquals(Recipe.LUNCH, rl.get(0).getMealType());
        assertEquals(Recipe.BREAKFAST, rl.get(1).getMealType());

    }




}