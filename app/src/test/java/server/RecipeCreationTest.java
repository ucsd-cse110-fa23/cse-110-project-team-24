/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package server;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import server.APIResponse;
import server.*;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeCreationTest {

    // This is just a random recipe from ChatGPT

    private static String EXAMPLE = "Title:   Whole-Grain Egg Bake\n" + //
            "\n" + //
            "Ingredients:\n" + //
            "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\n" + //
            "- 4 large eggs\n" + //
            "- 2 tablespoons milk\n" + //
            "- 1/4 teaspoon salt\n" + //
            "- 1/8 teaspoon freshly ground black pepper\n" + //
            "- 1/2 cup diced onion\n" + //
            "- 1/2 cup chopped bell pepper\n" + //
            "- 1/2 cup shredded cheddar cheese\n" + //
            "- 2 tablespoons olive oil\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)\n" + //
            "\n" + //
            "Instructions:\n" + //
            "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\n" + //
            "\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \n"
            + //
            "\n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    private final String EXPECTED_TITLE = "Whole-Grain Egg Bake";
    private static String EXPECTED_INGREDIENTS = "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\n"
            + //
            "- 4 large eggs\n" + //
            "- 2 tablespoons milk\n" + //
            "- 1/4 teaspoon salt\n" + //
            "- 1/8 teaspoon freshly ground black pepper\n" + //
            "- 1/2 cup diced onion\n" + //
            "- 1/2 cup chopped bell pepper\n" + //
            "- 1/2 cup shredded cheddar cheese\n" + //
            "- 2 tablespoons olive oil\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)";
    private static String EXPECTED_STEPS = "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\n" + //
            "\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \n"
            + //
            "\n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    
    private final String NO_NEWLINES = "Title: A title Ingredients: Ingredient 1, Ingredient 2 Instructions: Step 1, Step 2";
    private final String NO_NEWLINES_TITLE = "A title";
    private final String NO_NEWLINES_INGREDIENTS = "Ingredient 1, Ingredient 2";
    private final String NO_NEWLINES_INSTRUCTIONS = "Step 1, Step 2";

    private final String NO_FIELDS = "Title:Ingredients:Instructions:";


    @Test
    public void getTitleTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getTitle(EXAMPLE);
        assertEquals("Whole-Grain Egg Bake", expectedResult);

        String restOfRecipe = "Title:   %s\n\nIngredients:\nInstructions";
        String shortTitle = "Eggs";
        String shortTitleRecipe = String.format(restOfRecipe, shortTitle);
        assertEquals(shortTitle, generator.getTitle(shortTitleRecipe));

        String longTitle = "This is a very long title which should be included since it was generated!";
        String longTitleRecipe = String.format(restOfRecipe, longTitle);
        assertEquals(longTitle, generator.getTitle(longTitleRecipe));


        String multiLineTitle = "Line 1 of title\nAnother title Line\n yet another line";
        String multiLineTitleRecipe = String.format(restOfRecipe, multiLineTitle);
        assertEquals(multiLineTitle, generator.getTitle(multiLineTitleRecipe));


        assertEquals(NO_NEWLINES_TITLE, generator.getTitle(NO_NEWLINES));

        assertEquals("", generator.getTitle(NO_FIELDS));
    }

    @Test
    public void getIngredientsTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getIngredients(EXAMPLE);
        String expectedIngredients = EXPECTED_INGREDIENTS;
        assertEquals(expectedIngredients, expectedResult);

        String restOfRecipe = "Title:\n\nIngredients:\n%s\nInstructions:";
        String oneIngredient = "Just this ingredient";
        String manyIngredients = "Ingredient1\nINgredient2\nIngredient3\nIngredient4" +
            "\nIngredient5\nIngredient6\nIngredient7\nIngredient8\nIngredient9\nIngredient10";
        String noNewlines = "Ingredient1, Ingredient2, Ingredient3";
        
        String oneIngredientRecipe = String.format(restOfRecipe, oneIngredient);
        String manyIngredientsRecipe = String.format(restOfRecipe, manyIngredients);
        String noNewlinesRecipe = String.format(restOfRecipe, noNewlines);

        assertEquals(oneIngredient, generator.getIngredients(oneIngredientRecipe));
        assertEquals(manyIngredients, generator.getIngredients(manyIngredientsRecipe));
        assertEquals(noNewlines, generator.getIngredients(noNewlinesRecipe));

        assertEquals(NO_NEWLINES_INGREDIENTS, generator.getIngredients(NO_NEWLINES));

        assertEquals("", generator.getIngredients(NO_FIELDS));
    }

    @Test
    public void getInstructionsTest() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedResult = generator.getInstructions(EXAMPLE);
        assertEquals(EXPECTED_STEPS, expectedResult);

        assertEquals(NO_NEWLINES_INSTRUCTIONS, generator.getInstructions(NO_NEWLINES));

        assertEquals("", generator.getInstructions(NO_FIELDS));
    }

    @Test
    public void testGenerator() {
        APIResponse api = new MockAPIResponse(EXAMPLE);
        ChatGPTGenerator generator = new ChatGPTGenerator(api);
        String expectedTitle = EXPECTED_TITLE;
        String expectedIngredients = EXPECTED_INGREDIENTS;
        String expectedSteps = EXPECTED_STEPS;

        Recipe expected = new Recipe(expectedTitle, "breakfast", expectedIngredients, expectedSteps);
        Recipe result = generator.generateRecipe("breakfast", null);

        assertEquals(expected.ingredients, result.ingredients);
        assertEquals(expected.title, result.title);
        assertEquals(expected.steps, result.steps);
        assertEquals(true, expected.equals(result));
    }

    @Test
    public void storyTest() {
        /**
            * Scenario: Create Recipe
            * Given that I am in the List view
            * when I click on the “New Recipe” button,
            * then the app displays a menu labeled “Recipe type” with the options
            * breakfast, lunch, or dinner.
            * when I click on “breakfast”
            * then I am taken to the details view of a new recipe generated by ChatGPT
            */
            String mealType = "lunch";
            String recipe = "Title:   Some Recipe\n\nIngredients:\nIngredient 1\nIngredient2\nInstructions:\nStep 1\n Step 2\n";
            String title = "Some Recipe";
            String ingredients = "Ingredient 1\nIngredient2";
            String instructions = "Step 1\n Step 2\n";

            Recipe result = (new ChatGPTGenerator(new MockAPIResponse(recipe))).generateRecipe(mealType, "Some Ingredients");
            assertEquals(title, result.getTitle());
            assertEquals(ingredients, result.getIngredients());
            assertEquals(instructions, result.getSteps());

    }

}