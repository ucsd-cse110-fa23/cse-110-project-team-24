package PantryPal;
//package test; 

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class APITest {

// This is just a random recipe from ChatGPT
    
    private static String EXAMPLE = "Title: Whole-Grain Egg Bake\r\n" + //
            "\r\n" + //
            "Ingredients:\r\n" + //
            "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\r\n" + //
            "- 4 large eggs\r\n" + //
            "- 2 tablespoons milk\r\n" + //
            "- 1/4 teaspoon salt\r\n" + //
            "- 1/8 teaspoon freshly ground black pepper\r\n" + //
            "- 1/2 cup diced onion\r\n" + //
            "- 1/2 cup chopped bell pepper\r\n" + //
            "- 1/2 cup shredded cheddar cheese\r\n" + //
            "- 2 tablespoons olive oil\r\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)\r\n" + //
            "\r\n" + //
            "Instructions:\r\n" + //
            "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\r\n" + //
            "\r\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \r\n" + //
            "\r\n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    private final String EXPECTED_TITLE = "Whole-Grain Egg Bake";
    private static String EXPECTED_INGREDIENTS = "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\r\n" + //
            "- 4 large eggs\r\n" + //
            "- 2 tablespoons milk\r\n" + //
            "- 1/4 teaspoon salt\r\n" + //
            "- 1/8 teaspoon freshly ground black pepper\r\n" + //
            "- 1/2 cup diced onion\r\n" + //
            "- 1/2 cup chopped bell pepper\r\n" + //
            "- 1/2 cup shredded cheddar cheese\r\n" + //
            "- 2 tablespoons olive oil\r\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)\r\n";
    private static String EXPECTED_STEPS = "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\r\n" + //
            "\r\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \r\n" + //
            "\r\n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";


    @Test
    public void getTitleTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getTitle(EXAMPLE);
        assertEquals("Whole-Grain Egg Bake", expectedResult);
        //assertEquals(expectedResult, generator.getTitle(EXAMPLE));
    }

    @Test
    public void getIngredientsTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getIngredients(EXAMPLE);
        String expectedIngredients = EXPECTED_INGREDIENTS;
        assertEquals(expectedIngredients, expectedResult);
    }

    @Test
    public void getInstructionsTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getInstructions(EXAMPLE);
        String expectedSteps = EXPECTED_STEPS;
        assertEquals(expectedSteps, expectedResult);
    }

    @Test
    public void testGenerator() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedTitle = EXPECTED_TITLE ;
        String expectedIngredients = EXPECTED_INGREDIENTS;
        String expectedSteps = EXPECTED_STEPS;

        Recipe expected = new Recipe(expectedTitle, "breakfast", expectedIngredients, expectedSteps);
        Recipe result = generator.generateRecipe("breakfast", null);
        
        assertEquals(expected.ingredients, result.ingredients);
        assertEquals(expected.title, result.title);
        assertEquals(expected.steps, result.steps);
        assertEquals(true, expected.equals(result));
    }
    
}
