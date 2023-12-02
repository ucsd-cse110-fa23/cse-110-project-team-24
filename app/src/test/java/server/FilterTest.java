package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;



public class FilterTest {
    public static final String EXAMPLE_DATE_TIME = "2007-12-03T10:15:30+01:00[Europe/Paris]";
    Recipe r0 = new Recipe("r0", "Breakfast", null, null, EXAMPLE_DATE_TIME);
    Recipe r1 = new Recipe("r1", "Lunch", null, null, EXAMPLE_DATE_TIME);
    Recipe r2 = new Recipe("r2", "Dinner", null, null, EXAMPLE_DATE_TIME);
    Recipe r3 = new Recipe("r3", "Breakfast", null, null, EXAMPLE_DATE_TIME);
    Recipe r4 = new Recipe("r4", "Lunch", null, null, EXAMPLE_DATE_TIME);
    Recipe r5 = new Recipe("r5", "Dinner", null, null, EXAMPLE_DATE_TIME);
    


    @Test
    public void NoFilterTest() {
        // No items
        ArrayList<Recipe> input = new ArrayList<>();
        NoFilter nf = new NoFilter();
        assertEquals(0, nf.getModifiedList(input).size());

        // One item
        input.add(r0);
        assertEquals(r0, nf.getModifiedList(input).get(0));

        // Multiple items
        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);
        input.add(r5);


        assertEquals(r0, nf.getModifiedList(input).get(0));
        assertEquals(r1, nf.getModifiedList(input).get(1));
        assertEquals(r2, nf.getModifiedList(input).get(2));
        assertEquals(r3, nf.getModifiedList(input).get(3));
        assertEquals(r4, nf.getModifiedList(input).get(4));
        assertEquals(r5, nf.getModifiedList(input).get(5));
    }

    @Test
    public void BreakfastFilterTest() {
        // No items
        ArrayList<Recipe> input = new ArrayList<>();
        BreakfastFilter bf = new BreakfastFilter();
        assertEquals(0, bf.getModifiedList(input).size());

        // One item, filtered
        input.add(r1);
        assertEquals(0, bf.getModifiedList(input).size());

        // Multiple items, none filtered
        input.remove(0);
        input.add(r0);
        input.add(r3);

        List<Recipe> result = bf.getModifiedList(input);
        assertEquals(r0, result.get(0));
        assertEquals(r3, result.get(1));
        assertEquals(2, result.size());

        // Multiple items, some filtered
        input.remove(0);
        input.remove(0);
        input.add(r0);
        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);
        input.add(r5);

        result = bf.getModifiedList(input);
        assertEquals(r0, result.get(0));
        assertEquals(r3, result.get(1));
        assertEquals(2, result.size());

        // First item filtered
        input.add(0, r2);
        assertEquals(r0, bf.getModifiedList(input).get(0));

        // Last item filtered
        input.add(r2);
        result = bf.getModifiedList(input);
        assertEquals(r3, result.get(result.size() - 1));
    }


    @Test
    public void LunchFilterTest() {
        // No items
        ArrayList<Recipe> input = new ArrayList<>();
        LunchFilter lf = new LunchFilter();
        assertEquals(0, lf.getModifiedList(input).size());

        // One item, filtered
        input.add(r0);
        assertEquals(0, lf.getModifiedList(input).size());

        // Multiple items, none filtered
        input.remove(0);
        input.add(r1);
        input.add(r4);

        List<Recipe> result = lf.getModifiedList(input);
        assertEquals(r1, result.get(0));
        assertEquals(r4, result.get(1));
        assertEquals(2, result.size());

        // Multiple items, some filtered
        input.remove(0);
        input.remove(0);
        input.add(r0);
        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);
        input.add(r5);

        result = lf.getModifiedList(input);
        assertEquals(r1, result.get(0));
        assertEquals(r4, result.get(1));
        assertEquals(2, result.size());

        // First item filtered
        input.add(0, r2);
        assertEquals(r1, lf.getModifiedList(input).get(0));

        // Last item filtered
        input.add(r2);
        result = lf.getModifiedList(input);
        assertEquals(r4, result.get(result.size() - 1));
    }

    @Test
    public void DinnerFilterTest() {
        // No items
        ArrayList<Recipe> input = new ArrayList<>();
        DinnerFilter df = new DinnerFilter();
        assertEquals(0, df.getModifiedList(input).size());

        // One item, filtered
        input.add(r0);
        assertEquals(0, df.getModifiedList(input).size());

        // Multiple items, none filtered
        input.remove(0);
        input.add(r2);
        input.add(r5);

        List<Recipe> result = df.getModifiedList(input);
        assertEquals(r2, result.get(0));
        assertEquals(r5, result.get(1));
        assertEquals(2, result.size());

        // Multiple items, some filtered
        input.remove(0);
        input.remove(0);
        input.add(r0);
        input.add(r1);
        input.add(r2);
        input.add(r3);
        input.add(r4);
        input.add(r5);

        result = df.getModifiedList(input);
        assertEquals(r2, result.get(0));
        assertEquals(r5, result.get(1));
        assertEquals(2, result.size());

        // First item filtered
        input.add(0, r0);
        assertEquals(r2, df.getModifiedList(input).get(0));

        // Last item filtered
        input.add(r0);
        result = df.getModifiedList(input);
        assertEquals(r5, result.get(result.size() - 1));
    }

    

     @Test
     public void scenario1Test() {
        /**
        Scenario 1: Filtering Recipes
        Given that I am logged in and I have 2 recipes of type “Breakfast” and 3 recipes of other types
        When I click on Filter By
        Then a drop down menu with 4 options appears
        When I click on “Breakfast”
        Then the menu disappears and there are only 2 recipes (with meal type “Breakfast”) remaining in the list.
        Input: User stays at the Recipe List View with above recipes, 
            5 recipes in the list and 2 recipes are “Breakfast”,
            user clicks “Filter by”, and user clicks “Breakfast”
        Expected Output: 
            Recipe List with only two recipes of type “Breakfast” left in the Recipe List remain in the list. */
        RecipeList rl = new RecipeList(new ChronologicalSorter(), new NoFilter());
        Recipe r0 = new Recipe("r0", "Breakfast", null, null, "2002-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("r1", "Lunch", null, null, "2003-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe("r2", "Dinner", null, null, "2007-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r3 = new Recipe("r3", "Breakfast", null, null, "2004-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r4 = new Recipe("r4", "Lunch", null, null, "2005-12-03T10:15:30+01:00[Europe/Paris]");

        rl.add(r0);
        rl.add(r1);
        rl.add(r2);
        rl.add(r3);
        rl.add(r4);

        rl.setListModifyingStrategy("Breakfast");
        assertEquals(2, rl.size());
        assertEquals(r3, rl.get(0));
        assertEquals(r0, rl.get(1));
     }

     @Test
     public void scenario2Test() {
        /**
    
        Scenario 2: Remove Filter
        Given that I have 2 recipes of type “Breakfast” and 3 recipes of other types and I have just sorted using “Breakfast”
        When I click on Filter By
        Then a drop down menu with 4 options appears
        When I click on “None”
        Then the menu disappears and there are 5 recipes in the list.
        Input: Recipe List View sorted by “Breakfast” with above recipes, 
            “Filter by”, 
            “None”
        Expected Output: Recipe List with all 5 recipes mentioned above.
        */
        
        RecipeList rl = new RecipeList(new ChronologicalSorter(), new NoFilter());
        Recipe r0 = new Recipe("r0", "Breakfast", null, null, "2002-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r1 = new Recipe("r1", "Lunch", null, null, "2003-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r2 = new Recipe("r2", "Dinner", null, null, "2007-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r3 = new Recipe("r3", "Breakfast", null, null, "2004-12-03T10:15:30+01:00[Europe/Paris]");
        Recipe r4 = new Recipe("r4", "Lunch", null, null, "2005-12-03T10:15:30+01:00[Europe/Paris]");

        rl.add(r0);
        rl.add(r1);
        rl.add(r2);
        rl.add(r3);
        rl.add(r4);

        rl.setListModifyingStrategy("Breakfast");
        assertEquals(2, rl.size());
        assertEquals(r3, rl.get(0));
        assertEquals(r0, rl.get(1));

        rl.setListModifyingStrategy("NoFilter");    
        assertEquals(5, rl.size());
        assertEquals(r2, rl.get(0));
        assertEquals(r4, rl.get(1));
        assertEquals(r3, rl.get(2));
        assertEquals(r1, rl.get(3));
        assertEquals(r0, rl.get(4));

     }

     
}
