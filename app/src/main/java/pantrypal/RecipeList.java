package pantrypal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URLEncoder;
import java.net.URLDecoder;

public class RecipeList extends VBox {
    private String RecipeID;
    public RecipeList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
        // try {
        //     //this.loadRecipes();
        // } catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        // }
    }

    public void loadRecipes() throws UnsupportedEncodingException {
        String recipeId = this.getRecipeId();
        String recipes = PerformRequest.performRequest("RecipeDataGet", "GET", null, recipeId);
        recipes = URLDecoder.decode(recipes, "US-ASCII");
        
        String[] recipeArr;
        if (recipes.equals(""))
            // With no recipes, recipeArr should have 0 elements
            recipeArr = new String[]{};
        else 
            recipeArr = recipes.split("RECIPE_SEPARATOR");

        for (String components:recipeArr) {
            RecipeView StoredView = new RecipeView(Recipe.of(components));
            Button titleButton = StoredView.getTitle();            
            titleButton.setOnAction(e1 -> {
                try {
                    StoredView.OpenDetailView(StoredView.getStage(), this);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });
            this.getChildren().add(StoredView);
        }
    }
    public String getRecipeId(){
        return this.RecipeID;
    }

    public void setRecipeId(String ID){
         this.RecipeID = ID;
    }

    // Method to remove selected (deleted) recipes from the list
    public void removeSelectedRecipes() {
        // this.getChildren().removeIf(task -> task instanceof RecipeView &&
        // ((RecipeView) task).hasBeenDeleted());
        // Create a list to keep track of recipes to delete
        ArrayList<RecipeView> recipesToDelete = new ArrayList();
        // Iterate over the children of this VBox
        for (Node node : this.getChildren()) {
            // Check if the node is a RecipeView and has been marked for deletion
            if (node instanceof RecipeView && ((RecipeView) node).hasBeenDeleted()) {

                // Add the recipe view to the list of recipes to delete
                recipesToDelete.add((RecipeView) node);
                // Get the recipe associated with the view
                Recipe toDelete = ((RecipeView) node).getRecipe();
                // Call method to delete the recipe from backend
                DeleteBackendRecipe(toDelete);
            }
        }

        // Remove all marked recipes from the VBox children
        this.getChildren().removeAll(recipesToDelete);
    }


    // Method to send a request to the backend to delete a recipe
    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";"
                        + toDelete.getSteps());
    }
}