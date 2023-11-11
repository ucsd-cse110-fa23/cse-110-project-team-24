package pantrypal;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;


//For US 4-3
public class RecipeView extends HBox{
    private Boolean recipeDeleteButton = false;
    private Button titleButton;
    private Stage stage;
    private Recipe recipe;
    RecipeView(Recipe recipe) {
        this.recipe = recipe;

        stage = new Stage();       
        this.setPrefSize(1400, 100); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        

        titleButton = new Button(recipe.getTitle());
        titleButton.setPrefSize(1400, 100);      
        titleButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        this.getChildren().add(titleButton);
    }
    public boolean hasBeenDeleted(){
        return this.recipeDeleteButton;
    }    
    public Stage getStage(){
        return this.stage;
    }
    public Button getTitle(){
        return this.titleButton;
    }
    public String getTitleName(){
        return this.titleButton.getText();
    }
    public void setTitleName(String RecipeName){
        this.titleButton.setText(RecipeName);
        this.recipe.setTitle(RecipeName);
    }
    public void setRecipeDeleteButton(){
        this.recipeDeleteButton = true;
    }
    public void OpenDetailView(Stage stage, RecipeList recipeList){
        this.setStyle("-fx-border-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;");
        for (int i = 0; i < this.getChildren().size(); i++) {
            this.getChildren().get(i).setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
             // change color of recipe to green
        }
        DetailView viewinside = new DetailView(this.recipe);
		viewinside.OpenView(stage, this, recipeList);
    }

    public Recipe getRecipe() {
        return this.recipe;
    }
}
