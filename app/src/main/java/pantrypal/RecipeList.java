package pantrypal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RecipeList extends VBox {
    public RecipeList(){
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

     // Method to remove selected (deleted) recipes from the list
    public void removeSelectedRecipes() {
        // this.getChildren().removeIf(task -> task instanceof RecipeView && ((RecipeView) task).hasBeenDeleted());
         // Create a list to keep track of recipes to delete 
        ArrayList<RecipeView> recipesToDelete = new ArrayList();
        // Iterate over the children of this VBox
        for (Node node:this.getChildren()){
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
    public void loadRecipe() throws IOException, CsvException{
        File file = new File("StoredRecipe.csv");
        FileReader filereader = new FileReader(file);
        CSVReader csvReader = new CSVReader(filereader);
        List<String[]> allRows = csvReader.readAll();
        for (String[] row : allRows) {
            Recipe Stored = new Recipe(row[0], row[1], row[2], row[3]);
            RecipeView StoredView = new RecipeView(Stored);
            Button titleButton = StoredView.getTitle();            
            titleButton.setOnAction(e1 -> {
                try {
                    StoredView.OpenDetailView(StoredView.getStage(), this);
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            });
            this.getChildren().add(StoredView);
        }
        csvReader.close();
    }

    public void saveRecipe() throws IOException{
        File file = new File("StoredRecipe.csv");
        String[] row = new String[4];
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        for (int i = 0; i < this.getChildren().size(); i++) {
            if(this.getChildren().get(i) instanceof RecipeView){
                RecipeView recipeView =  (RecipeView) this.getChildren().get(i);
                Recipe recipe = recipeView.getRecipe();
                row[0] = recipe.getTitle();
                row[1] = recipe.getMealType();
                row[2] = recipe.getIngredients();
                row[3] = recipe.getSteps();
                csvWriter.writeNext(row);                
        }
    }
    csvWriter.close();
    }
    // Method to send a request to the backend to delete a recipe
    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";" + toDelete.getSteps());
    }
}