package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


public class US2SortListStoryTest {
    @Test
    public void scenario1Test() {
        // Scenario 1: Sort List “Alphabetically”
        // Given that the user logs in and stays at the Recipe List view, there are two recipes called “Tofu with soy sauce” created at 11/18  and “American Burger” created at 11/19, “Fries” created at 11/10
        // When the user clicks on the button “Sort List,”
        // Then a new dropdown pops up, asking the user to choose which methods should be used: “Alphabetically,” “Chronologically from oldest to newest,” or “Chronologically from newest to oldest.”
        // When the user clicks on “Alphabetically”, 
        // Then the “American Burger” becomes the first of the recipe list, “Fries” becomes the second, and “Tofu with soy sauce” becomes the third.
        // Input: 	User enters Recipe List View,
        // User clicks “Sort List”,
        // User Clicks “Alphabetically.”
        // Expected Output: 	the recipe is sorted by Alphabetical order (From A - Z), 
        // “American Burger” should be the first,
        // “Fries” is the second, 
        // “Tofu with soy sauce” is the third.
        RecipeList recipeList = new RecipeList(new ChronologicalSorter());
        Recipe r0 = new Recipe("American Burger", null, null, null, "2020-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("Tofu with soy sauce", null, null, null, "2000-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe("Fries", null, null, null,  "1999-12-03T10:15:30+01:00[Europe/Paris]");
        recipeList.add(r0);
        recipeList.add(r1);
        recipeList.add(r2);

        recipeList.setListModifyingStrategy("Alphabetical");
        assertEquals(r0, recipeList.get(0));
        assertEquals(r2, recipeList.get(1));
        assertEquals(r1, recipeList.get(2));
    }

    @Test
    public void scenario2Test() {
        // Scenario 2: Sort List “Chronologically from oldest to newest”
        // Given that the user logs in and stays at the Recipe List view, there are two recipes called “Tofu with soy sauce” created at 11/18  and “American Burger” created at 11/19, “Fries” created at 11.10
        // When the user clicks on the button “Sort List,”
        // Then a new dropdown pops up, asking the user to choose which methods should be used: “Alphabetically,” “Chronologically from oldest to newest,” or “Chronologically from newest to oldest.”
        // When the user clicks on “Chronologically from oldest to newest”, 
        // Then “Fries” becomes the first of the recipe list, “Tofu with soy sauce” becomes the second, “American Burger” becomes the third.

        // Input: 	Recipe List View with above recipes, 
        // “Sort List”, 
        // “Chronologically from oldest to newest”
        // Expected Output: the recipe is sorted by Chronological order (From oldest to newest),
        // “Fries” should be the first,
        // “Tofu with soy sauce” is the second,
        // “American Burger” is the third.

        RecipeList recipeList = new RecipeList(new ChronologicalSorter());
        Recipe r0 = new Recipe("American Burger", null, null, null, "2020-11-19T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("Tofu with soy sauce", null, null, null, "2000-11-18T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe("Fries", null, null, null,  "1999-11-10T10:15:30+01:00[Europe/Paris]");
        recipeList.add(r0);
        recipeList.add(r1);
        recipeList.add(r2);

        recipeList.setListModifyingStrategy(new ReverseChronologicalSorter());
        assertEquals (r2, recipeList.get(0));
        assertEquals (r1, recipeList.get(1));
        assertEquals (r0, recipeList.get(2));
    }


    @Test
    public void scenario3Test() {
        // Scenario 3: Sort List “Chronologically from newest to oldest”
        // Given that the user logs in and stays at the Recipe List view, there are 3 recipes called “Tofu with soy sauce” created at 11/18  and “American Burger” created at 11/19 and "Fries" created 11/10
        // When the user clicks on the button “Sort List,”
        // Then a new dropdown pops up, asking the user to choose which methods should be used: “Alphabetically,” “Chronologically from oldest to newest,” or “Chronologically from newest to oldest.”
        // When the user clicks on “Chronologically from newest to oldest”, 
        // Then “American Burger” becomes the first of the recipe list, “Toy with soy sauce” becomes the second, “Fries” becomes the third.

        // Input: Recipe List View with above recipes, 
        // User clicks “Sort List”, 
        // and User Clicks “Chronologically from newest to oldest”
        // Expected Output: 
        // the recipe is sorted by Chronological order (From newest to oldest),
        //  “American Burger” should be the first, 
        // “Tofu with soy sauce” is the second,
        //  “Fries” is the third.

        RecipeList recipeList = new RecipeList(new ChronologicalSorter());
        Recipe r0 = new Recipe("American Burger", null, null, null, "2020-11-19T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("Tofu with soy sauce", null, null, null, "2000-11-18T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe("Fries", null, null, null,  "1999-11-10T10:15:30+01:00[Europe/Paris]");
        recipeList.add(r0);
        recipeList.add(r1);
        recipeList.add(r2);

        recipeList.setListModifyingStrategy(new ChronologicalSorter());
        assertEquals (r0, recipeList.get(0));
        assertEquals (r1, recipeList.get(1));
        assertEquals (r2, recipeList.get(2));
    }
}

