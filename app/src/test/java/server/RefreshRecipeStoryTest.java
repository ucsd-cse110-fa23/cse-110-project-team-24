package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
public class RefreshRecipeStoryTest {

    @Test
    public void scenario1Test() {
        /**
       Scenario 1: Refresh Recipe
        Given that the user logged in and stayed at the Created Recipe view, a recipe called “Fried tomatoes and potatoes” is shown,
        When I click the button “Create another Recipe”, 
        Then another recipe which also uses tomatoes and potatoes, called “Potato and tomato curry”, shows up with a preview image.
        Input: 	The user stays in Created Recipe view, 
        User clicks “Create another Recipe”
        Expected Output: Another Recipe using the same ingredients is shown in Created Recipe view
         */

        String mealType = "Lunch";
        String ingredients = "tomatoes, potatoes";

        Recipe originalRecipe = new Recipe("Fried tomatoes and potatoes", 
                mealType, "tomatoes, potatoes", "fry tomatoes", "2020-12-03T10:15:30+01:00[Europe/Paris]");

        String newResponse = String.format("%s;%s;%s;%s;%s", "Stir Fry tomatoes and potato", 
                mealType, ingredients, "stir fry stuff", "2020-12-03T10:15:30+01:00[Europe/Paris]");
        MockAPIResponse gpt = new MockAPIResponse(newResponse);
        Recipe newRecipe = Recipe.of(gpt.getText(mealType, ingredients));

        assertEquals(false, newRecipe.getTitle().equals(originalRecipe.getTitle()));
        assertEquals(newRecipe.getMealType(), originalRecipe.getMealType());
        assertEquals(newRecipe.getIngredients(), originalRecipe.getIngredients());
        assertEquals(false, newRecipe.getSteps().equals(originalRecipe.getSteps()));
    }
    
}
