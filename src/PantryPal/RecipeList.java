package PantryPal;
import javafx.scene.layout.VBox;

class RecipeList extends VBox {
    RecipeList(){
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
    public void removeSelectedRecipes() {
        this.getChildren().removeIf(task -> task instanceof RecipeView && ((RecipeView) task).getDeleteit());
    }
}