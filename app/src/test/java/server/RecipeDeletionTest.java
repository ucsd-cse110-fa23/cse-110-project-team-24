package server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import pantrypal.Recipe;
import pantrypal.RecipeList;
import pantrypal.RecipeView;

public class RecipeDeletionTest {

    private RecipeList recipeList;

    @BeforeEach
    public void setUp() throws Exception {
        recipeList = new RecipeList();
        // Add a couple of recipes for testing
        Recipe recipe1 = new Recipe("Recipe1", "Breakfast", "Ingredients1", "Instructions1");
        Recipe recipe2 = new Recipe("Recipe2", "Lunch", "Ingredients2", "Instructions2");
        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
    }

    @Test
    public void testRemoveSelectedRecipes() throws IOException {
        // Mark one recipe for deletion
        for (Node node : recipeList.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView view = (RecipeView) node;
                if (view.getRecipe().getTitle().equals("Recipe1")) {
                    view.setRecipeDeleteButton(true); // Assuming this method marks the recipe as deleted
                }
            }
        }

        // Call removeSelectedRecipes method
        recipeList.removeSelectedRecipes();

        // Verify that the recipe is removed
        boolean recipeExists = false;
        for (Node node : recipeList.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView view = (RecipeView) node;
                if (view.getRecipe().getTitle().equals("Recipe1")) {
                    recipeExists = true;
                    break;
                }
            }
        }
        assertFalse(recipeExists, "Recipe1 should be deleted from RecipeList");

        // Optionally, verify backend deletion
        // This depends on how your backend deletion is implemented
    }
}









/*package server;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import pantrypal.RecipeList;
import pantrypal.RecipeView;


public class RecipeDeletionTest {
    private RecipeList recipeList ;

    String Egg_Bake = "Whole-Grain Egg Bake";
    String mealType1 = "breakfast";
    String responseIngredients1 = "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\n" + //
        "- 4 large eggs\n" + //
        "- 2 tablespoons milk\n" + //
        "- 1/4 teaspoon salt\n" + //
        "- 1/8 teaspoon freshly ground black pepper\n" + //
        "- 1/2 cup diced onion\n" + //
        "- 1/2 cup chopped bell pepper\n" + //
        "- 1/2 cup shredded cheddar cheese\n" + //
        "- 2 tablespoons olive oil\n" + //
        "- Chopped fresh herbs, such as parsley or chives (optional)\n";
    String instructions1 = "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\n" + //
        "\n" + //
        "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \n" + //
        "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    //Recipe recipe1 = new Recipe(Egg_Bake, mealType1, responseIngredients1, instructions1);

    Recipe avocadoSalad = new Recipe(
        "Avocado and Chickpea Salad", 
        "Lunch",
        "Ripe avocado, diced; Cooked chickpeas; Cherry tomatoes, halved; Red onion, finely chopped; Lemon juice; Olive oil; Salt and pepper", 
        "1. In a bowl, combine diced avocado, chickpeas, halved cherry tomatoes, and chopped red onion. 2. Drizzle with lemon juice and olive oil. 3. Season with salt and pepper, and toss gently. Serve chilled or at room temperature."
    );

    @Test
    public void testDeleteRecipe() throws Exception {
        recipeList = new RecipeList();
        pantrypal.Recipe recipe1 = new pantrypal.Recipe(avocadoSalad);
        pantrypal.Recipe recipe2 = new pantrypal.Recipe("Whole-Grain Egg Bake", mealType1, responseIngredients1, instructions1);
        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
        recipeList.saveRecipe();

        String recipeTitleToDelete = "Whole-Grain Egg Bake"; // Use the exact title

        // Delete the recipe
        recipeList.deleteRecipe(recipeTitleToDelete);

        // Verify deletion from RecipeList
        boolean recipeExists = false;
        for (Node node : recipeList.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView view = (RecipeView) node;
                if (view.getRecipe().getTitle().equalsIgnoreCase(recipeTitleToDelete)) {
                    recipeExists = true;
                    break;
                }
            }
        }
        assertFalse(recipeExists, "Recipe should be deleted from RecipeList");

        // Optionally, verify deletion from CSV file
    }

    


}
 */

/* 
    @Test
    public void testDeleteRecipe() throws Exception {

        pantrypal.Recipe recipe1 = new pantrypal.Recipe(Egg_Bake, mealType1, responseIngredients1, instructions1);
        pantrypal.Recipe recipe2 = new pantrypal.Recipe(
            "Avocado and Chickpea Salad", 
            "Lunch",
            "Ripe avocado, diced; Cooked chickpeas; Cherry tomatoes, halved; Red onion, finely chopped; Lemon juice; Olive oil; Salt and pepper", 
            "1. In a bowl, combine diced avocado, chickpeas, halved cherry tomatoes, and chopped red onion. 2. Drizzle with lemon juice and olive oil. 3. Season with salt and pepper, and toss gently. Serve chilled or at room temperature."
        );
        recipeList =  new RecipeList();
        // you can use loadRecipe to Load initial recipes instead of creating new recipes
        //recipeList.loadRecipe(); 
        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
        recipeList.saveRecipe();

        String recipeTitleToDelete = "Egg_Bake"; 

        // Delete the recipe
        recipeList.deleteRecipe(recipeTitleToDelete);

        // Verify that the recipe is removed from RecipeList
        boolean recipeExists = false;
        for (Node node : recipeList.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView view = (RecipeView) node;
                if (view.getRecipe().getTitle().equalsIgnoreCase(recipeTitleToDelete)) {
                    recipeExists = true;
                    break;
                }
            }
        }
        assertFalse(recipeExists, "Recipe should be deleted from RecipeList");

    }
    */
/*
 * 
 * @Test
    public void deleteRecipe() throws Exception{
       // recipeList.addRecipe(recipe1);
        pantrypal.Recipe recipe1 = new pantrypal.Recipe(Egg_Bake, mealType1, responseIngredients1, instructions1);
        pantrypal.Recipe recipe2 = new pantrypal.Recipe(
            "Avocado and Chickpea Salad", 
            "Lunch",
            "Ripe avocado, diced; Cooked chickpeas; Cherry tomatoes, halved; Red onion, finely chopped; Lemon juice; Olive oil; Salt and pepper", 
            "1. In a bowl, combine diced avocado, chickpeas, halved cherry tomatoes, and chopped red onion. 2. Drizzle with lemon juice and olive oil. 3. Season with salt and pepper, and toss gently. Serve chilled or at room temperature."
        );
        recipeList =  new RecipeList();
        // you can use loadRecipe to Load initial recipes instead of creating new recipes
        //recipeList.loadRecipe(); 
        recipeList.addRecipe(recipe1);
        recipeList.addRecipe(recipe2);
        recipeList.saveRecipe();

    }
 */
