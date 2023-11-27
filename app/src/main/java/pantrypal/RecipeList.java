package pantrypal;
import java.io.*;
import java.util.*;

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


// I just want to use it for the delete a recipe 
    public void addRecipe(Recipe newRecipe) throws Exception {
        // Check if a recipe with the same title already exists
        for (Node node : this.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView view = (RecipeView) node;
                if (view.getRecipe().getTitle().equalsIgnoreCase(newRecipe.getTitle())) {
                    // Recipe title already exists, handle throw an exception
                    throw new Exception("A recipe with that name already exists.");
                }
            }
        }
        RecipeView recipeView = new RecipeView(newRecipe);
        this.getChildren().add(recipeView);
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

   
   // Assumption 
   //there is only one recipe with this specific title
    public void deleteRecipe(String recipeTitle) throws IOException {
        // Step 1: Update RecipeList and RecipeView
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeView) {
                RecipeView recipeView = (RecipeView) this.getChildren().get(i);
                Recipe recipe = recipeView.getRecipe();
                if (recipe.getTitle().equalsIgnoreCase(recipeTitle)) {
                    this.getChildren().remove(i); // Remove from the view
                    break; 
                }
            }
        }
       // Update the CSV file
        updateCSV();    
    }

    // Method to send a request to the backend to delete a recipe
    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";" + toDelete.getSteps());
    }


    private void updateCSV() throws IOException {
        File file = new File("StoredRecipe.csv");
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(fileWriter);

        // Iterate over the children of this VBox and write each Recipe to the CSV file
        for (Node node : this.getChildren()) {
            if (node instanceof RecipeView) {
                RecipeView recipeView = (RecipeView) node;
                Recipe recipe = recipeView.getRecipe();
                String[] row = new String[4];
                row[0] = recipe.getTitle();
                row[1] = recipe.getMealType();
                row[2] = recipe.getIngredients();
                row[3] = recipe.getSteps();
                csvWriter.writeNext(row);
            }
        }

        // Close the CSVWriter
        csvWriter.close();
    }
}


    /*
     // Update the CSV file
        File inputFile = new File("StoredRecipe.csv");
        File tempFile = new File("tempStoredRecipe.csv");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        String[] row;
        while ((currentLine = reader.readLine()) != null) {
            row = currentLine.split(","); // Assuming your CSV uses commas as a delimiter
            if (!row[0].equalsIgnoreCase(recipeTitle)) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        // Delete the original file and rename the temp file to the original file name
        if (!inputFile.delete()) {
            System.out.println("Could not delete the original file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename the temp file");
            return;
        }
     */