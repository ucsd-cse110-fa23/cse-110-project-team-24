package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeManagerTest {
    private MockRecipeManger mockRecipeManager;

    @BeforeEach
    public void setUp() {
        mockRecipeManager = new MockRecipeManger();
    }

     @Test
    public void testInsertRecipe() {
        mockRecipeManager.insertRecipe("Spaghetti", "Dinner", "Pasta, Tomato Sauce", "Cook pasta, add sauce");
        Document insertedRecipe = mockRecipeManager.findRecipesByTitle("Spaghetti");
        assertNotNull(insertedRecipe);
        assertEquals("Spaghetti", insertedRecipe.getString("title"));
        assertEquals("Dinner", insertedRecipe.getString("mealType"));
    } 

    @Test
    public void testDeleteRecipeByTitle() {
        mockRecipeManager.insertRecipe("Lasagna", "Dinner", "Pasta, Cheese", "Layer and bake");
        assertTrue(mockRecipeManager.deleteRecipeByTitle("Lasagna"));
        assertNull(mockRecipeManager.findRecipesByTitle("Lasagna"));
    }

    @Test
    public void testGetRecipesByUser() {
        mockRecipeManager.insertUserRecipe("safia", "Pizza", "Dinner", "Dough, Cheese, Tomato", "Bake pizza");
        var userRecipes = mockRecipeManager.getRecipesByUser("safia");
        assertEquals(1, userRecipes.size());
        assertEquals("Pizza", userRecipes.get(0).getString("title"));
    }
}
