package pantrypal;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

class RecipeList extends VBox {
    RecipeList(){
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

    // Method to send a request to the backend to delete a recipe
    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";" + toDelete.getSteps());
    }
}