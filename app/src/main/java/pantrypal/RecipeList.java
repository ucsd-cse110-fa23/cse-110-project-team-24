package pantrypal;
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
        // TODO: tell server to delete
        for (Node node:this.getChildren()){
            if (node instanceof RecipeView && ((RecipeView) node).hasBeenDeleted()) {
                this.getChildren().remove(node);
                Recipe toDelete = ((RecipeView) node).getRecipe();
                DeleteBackendRecipe(toDelete);
            }
        }
    }

    private void DeleteBackendRecipe(Recipe toDelete) {
        PerformRequest.performRequest("/", "DELETE", null,
                toDelete.getTitle() + ";" + toDelete.getMealType() + ";" + toDelete.getIngredients() + ";" + toDelete.getSteps());
    }
}