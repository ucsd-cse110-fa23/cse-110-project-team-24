package server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MockRecipeManger {

    private List<Document> mockRecipes = new ArrayList<>();

    // Simulate inserting a single recipe
    public void insertRecipe(String title, String mealType, String ingredients, String instructions) {
        Document newRecipe = new Document("_id", new ObjectId())
            .append("title", title)
            .append("mealType", mealType)
            .append("ingredients", ingredients)
            .append("instructions", instructions);
        mockRecipes.add(newRecipe);
    }

    public void insertUserRecipe(String username, String title, String mealType, String ingredients, String instructions) {
        Document newRecipe = new Document("_id", new ObjectId())
            .append("username", username)
            .append("title", title)
            .append("mealType", mealType)
            .append("ingredients", ingredients)
            .append("instructions", instructions);
        mockRecipes.add(newRecipe);
    }

    // Simulate finding a recipe by title
    public Document findRecipesByTitle(String recipeTitle) {
        for (Document recipe : mockRecipes) {
            if (recipeTitle.equals(recipe.getString("title"))) {
                return recipe;
            }
        }
        return null;
    }

    public List<Document> getRecipesByUser(String username) {
        List<Document> userRecipes = new ArrayList<>();
        for (Document recipe : mockRecipes) {
            if (username.equals(recipe.getString("username"))) {
                userRecipes.add(recipe);
            }
        }
        return userRecipes;
    }
    

    public boolean updateRecipe(String recipeID, Document updatedDoc) {
        boolean isUpdated = false;
        for (Document recipe : mockRecipes) {
            if (recipe.getObjectId("_id").toString().equals(recipeID)) {
                for (String key : updatedDoc.keySet()) {
                    recipe.put(key, updatedDoc.get(key));
                }
                isUpdated = true;
            }
        }
        return isUpdated;
    }
    

    public boolean deleteRecipeByTitle(String title) {
        boolean isRemoved = false;
        for (int i = 0; i < mockRecipes.size(); i++) {
            if (title.equals(mockRecipes.get(i).getString("title"))) {
                mockRecipes.remove(i);
                isRemoved = true;
                break; // Break after the first match
            }
        }
        return isRemoved;
    }
}

