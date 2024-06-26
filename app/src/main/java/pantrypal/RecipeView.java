package pantrypal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.*;


//For US 4-3
// RecipeView class for displaying a single recipe in the UI
public class RecipeView extends StackPane{
    // Flag to track if the recipe is marked for deletion
    private Boolean recipeDeleteButton = false;
    private Button titleButton;
    private Stage stage;
    private Recipe recipe;
    private Label mealTypeLabel;

    public RecipeView(Recipe recipe) {
        this.recipe = recipe;
        this.setAlignment(Pos.BOTTOM_RIGHT);
        stage = new Stage();
        this.setPrefSize(1400, 100); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        

        titleButton = new Button(recipe.getTitle());
        titleButton.setPrefSize(1400, 100);
        titleButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");

        mealTypeLabel = new Label(recipe.getMealType());
        mealTypeLabel.setPadding(new Insets(10, 10, 10, 10));
        String kellyGreen = "rgb(102, 204, 0)";
        mealTypeLabel.setStyle("-fx-background-color:" + kellyGreen + "; -fx-fill:" + kellyGreen +";");
        this.getChildren().add(titleButton);
        this.getChildren().add(mealTypeLabel); // there. but not visible
    }

    // Method to check if the recipe is marked for deletion
    public boolean hasBeenDeleted(){
        return this.recipeDeleteButton;
    }

    // Getter for the associated stage
    public Stage getStage(){
        return this.stage;
    }
    // Getter for the title button
    public Button getTitle(){
        return this.titleButton;
    }
    // Getter for the title name
    public String getTitleName(){
        return this.titleButton.getText();
    }

     // Setter for the title name and updates the recipe title
    public void setTitleName(String RecipeName){
        this.titleButton.setText(RecipeName);
        this.recipe.setTitle(RecipeName);
    }

     // Setter to mark the recipe as deleted
    public void setRecipeDeleteButton(){
        this.recipeDeleteButton = true;
    }

     // Method to open the detailed view of the recipe
    public void OpenDetailView(Stage stage, RecipeList recipeList) throws Exception{
        this.setStyle("-fx-border-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;");
        for (int i = 0; i < this.getChildren().size(); i++) {
            this.getChildren().get(i).setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
             // change color of recipe to green
        }
        DetailView viewinside = new DetailView(this.recipe);
		viewinside.OpenView(stage, this, recipeList);
    }

    // Getter for the recipe
    public Recipe getRecipe() {
        return this.recipe;
    }
    public RecipeView copy(){
        RecipeView recipes = new RecipeView(recipe);
        return recipes;
    }

    public void update(Recipe r) {
        this.recipe = r;
        this.setTitleName(r.getTitle());
    }
}
