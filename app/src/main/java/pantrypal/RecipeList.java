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
    public void removeSelectedRecipes() {
        // this.getChildren().removeIf(task -> task instanceof RecipeView && ((RecipeView) task).hasBeenDeleted());
        ArrayList<RecipeView> recipesToDelete = new ArrayList();
        for (Node node:this.getChildren()){
            if (node instanceof RecipeView && ((RecipeView) node).hasBeenDeleted()) {
                recipesToDelete.add((RecipeView) node);
                Recipe toDelete = ((RecipeView) node).getRecipe();
                DeleteBackendRecipe(toDelete);
            }
        }

        this.getChildren().removeAll(recipesToDelete);
    }

    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";" + toDelete.getSteps());
    }
}