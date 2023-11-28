package server;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;
import server.APIResponse;
import server.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class MultiplePlatformsTest {
    private final String ENCODING = "US-ASCII";
    private final String RECIPE_SEPARATOR = "RECIPE_SEPARATOR";
    // Basic case
    String title1 = "RecipeTitle";
    String type1 = "RecipeType";
    String ingredients1 = "RecipeIngredients";
    String instructions1 = "RecipeInstructions";

    // Whitespace characters
    String title2= "Super difficult and long title which is not easily encoded\r\n"+ //
    "";
    String type2 = "Breakfast";
    String ingredients2="it has multiple\r\n"+ //
        "lines \r\n"+ //
        "here";
    String instructions2 = "Step 1\r\n"+ //
        "Step 2\r\n"+ //
        "Step 3\r\n"+ //
        "Step 4";

    // Special characters
    String title3 = "$%(*7\r\n" + //
            "";
    String type3 = "Dinner";
    String ingredients3 = "A bunch of special characters $(*&$34I#*(&(*&(*\r\n" + //
            "";
    String instructions3 = "\"Hope this encoding works . . .\"[[{}}]\\/\r\n" + //
            "";

    ArrayList<Recipe> recipes;

    @BeforeEach
    public void addRecipes() {
        this.recipes = new ArrayList<>();
        recipes.add(0, new Recipe(title1, type1, ingredients1, instructions1));
        recipes.add(1, new Recipe(title2, type2, ingredients2, instructions2));
        recipes.add(2, new Recipe(title3, type3, ingredients3, instructions3));
    }

    @Test
    public void testHandlePost() {
        ArrayList<Recipe> recipes = new ArrayList();
        MockBaseHandler handler = new MockBaseHandler(recipes);

        try {
            String title = "RecipeTitle";
            String type = "RecipeType";
            String ingredients = "RecipeIngredients";
            String instructions = "RecipeInstructions";
            handler.handlePut(new MockExchange("https://localhost/", URLEncoder.encode(title + ";" + type + ";" + ingredients + ";" + instructions, ENCODING)));
            Recipe added = recipes.get(0);
            assertEquals(title, added.getTitle());
            assertEquals(type, added.getMealType());
            assertEquals(ingredients, added.getIngredients());
            assertEquals(instructions, added.getSteps());

            // Special characters
            title = "$%(*7\r\n" + //
                    "";
            type = "Dinner";
            ingredients = "A bunch of special characters $(*&$I#*(&(*&(*\r\n" + //
                    "";
            instructions = "\"Hope this encoding works . . .\"[[{}}]\\/\r\n" + //
                    "";
            handler.handlePut(new MockExchange("https://localhost/", URLEncoder.encode(title + ";" + type + ";" + ingredients + ";" + instructions, ENCODING)));
            added = recipes.get(0);
            assertEquals(title, added.getTitle());
            assertEquals(type, added.getMealType());
            assertEquals(ingredients, added.getIngredients());
            assertEquals(instructions, added.getSteps());

            //Whitespace Characters
            title = "Super difficult and long title which is not easily encoded\r\n" + //
                    "";
            type = "Breakfast";
            ingredients = "it has multiple\r\n" + //
                    "lines \r\n" + //
                    "here";
            instructions = "Step 1\r\n" + //
                    "Step 2\r\n" + //
                    "Step 3\r\n" + //
                    "Step 4";
            handler.handlePut(new MockExchange("https://localhost/", URLEncoder.encode(title + ";" + type + ";" + ingredients + ";" + instructions, ENCODING)));
            added = recipes.get(0);
            assertEquals(title, added.getTitle());
            assertEquals(type, added.getMealType());
            assertEquals(ingredients, added.getIngredients());
            assertEquals(instructions, added.getSteps());

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @Test
    public void handlePostTest() {
        String newIngredients = "Woah new ingredients";
        MockBaseHandler handler = new MockBaseHandler(recipes);
        try {
            handler.handlePost(new MockExchange("https://localhost/", 
                    URLEncoder.encode(title1 + ";" + type1 + ";" +  newIngredients + ";" + instructions1, ENCODING)));
            Recipe actual = recipes.get(0);
            assertEquals(title1, actual.getTitle());
            assertEquals(type1, actual.getMealType());
            assertEquals(newIngredients, actual.getIngredients());
            assertEquals(instructions1, actual.getSteps());

            String newInstructions = "These are new instructions for the second test";
            handler.handlePost(new MockExchange("https://localhost/", 
                    URLEncoder.encode(title2 + ";" + type2 + ";" +  ingredients2 + ";" + newInstructions, ENCODING)));
            actual = recipes.get(1);
            assertEquals(title2, actual.getTitle());
            assertEquals(type2, actual.getMealType());
            assertEquals(ingredients2, actual.getIngredients());
            assertEquals(newInstructions, actual.getSteps());

            newInstructions = "These instuction \n have lots\n\n of ()*&)(&{Special Characters})";
            handler.handlePost(new MockExchange("https://localhost/", 
                    URLEncoder.encode(title3 + ";" + type3 + ";" +  ingredients3 + ";" + newInstructions, ENCODING)));
            actual = recipes.get(2);
            assertEquals(title3, actual.getTitle());
            assertEquals(type3, actual.getMealType());
            assertEquals(ingredients3, actual.getIngredients());
            assertEquals(newInstructions, actual.getSteps());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handleDeleteTest() {
        try {
            BaseHandler handler = new BaseHandler(recipes);
            handler.handlePost(new MockExchange("https://localhost/" + "?=" + 
                    URLEncoder.encode(title2 + ";" + type2 + ";" +  ingredients2 + ";" + instructions2, ENCODING), null));
            Recipe actual = recipes.get(0);
            assertEquals(title1, actual.getTitle());
            assertEquals(type1, actual.getMealType());
            assertEquals(ingredients1, actual.getIngredients());
            assertEquals(instructions1, actual.getSteps());
            
            actual = recipes.get(1);
            assertEquals(title3, actual.getTitle());
            assertEquals(type3, actual.getMealType());
            assertEquals(ingredients3, actual.getIngredients());
            assertEquals(instructions3, actual.getSteps());
            

            handler.handlePost(new MockExchange("https://localhost/" + "?=" + 
                    URLEncoder.encode(title1 + ";" + type1 + ";" +  ingredients1 + ";" + instructions1, ENCODING), null));
            actual = recipes.get(0);
            assertEquals(title3, actual.getTitle());
            assertEquals(type3, actual.getMealType());
            assertEquals(ingredients3, actual.getIngredients());
            assertEquals(instructions3, actual.getSteps());

            handler.handlePost(new MockExchange("https://localhost/" + "?=" + 
                    URLEncoder.encode(title3 + ";" + type3 + ";" +  ingredients3 + ";" + instructions3, ENCODING), null));
            assertEquals(0, recipes.size());
        } catch (Exception e) {
        }
    }

    @Test
    public void testHandleGet() throws UnsupportedEncodingException {
        MockBaseHandler handler = new MockBaseHandler(recipes);
        String expected = title1 + ";" + type1 + ";" + ingredients1 + ";" + instructions1 + 
                RECIPE_SEPARATOR + title2 + ";" + type2 + ";" + ingredients2 + ";" + instructions2 + 
                RECIPE_SEPARATOR + title3 + ";" + type3 + ";" + ingredients3 + ";" + instructions3;
        String actual = handler.handleGet(null);
    }

    @Test
    public void testGetParsedAndDecodedQuery() {
        try {
            String expected = "Some really )(*)*$(*&%)\nbdifficult query";
            String query = "?=" + URLEncoder.encode(expected, ENCODING);
            String result = GenerationHandler.getParsedAndDecodedQuery(query);
            assertEquals(expected, result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    // Helper method to aid debugging
    private static void printList (ArrayList<Recipe> recipes) {
        for (Recipe recipe: recipes) {
            System.out.println(recipe.toString());
        }
    }
}