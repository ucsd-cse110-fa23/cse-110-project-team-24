package pantrypal;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

// Footer class extends HBox to arrange its children in a horizontal row
class Footer extends HBox {
    private Button Create;
    private Button Sort;
    ComboBox<String> comboBox = new ComboBox<>();
    RecipeList recipeList;
    // Constructor for Footer
    Footer(RecipeList recipeList) {
        this.recipeList = recipeList;
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        Sort = new Button("Sort");
        
        comboBox.getItems().add("Alphabetically");
        comboBox.getItems().add("Chronologically Old to New");
        comboBox.getItems().add("Chronologically New to Old");
         EventHandler<ActionEvent> event =
                  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                String result = comboBox.getValue();
                if(result.equals("Alphabetically")){
                    recipeList.SortRecipeListAlphabetically();
                }
                if(result.equals("Chronologically Old to New")){
                    recipeList.SortRecipeListChronologicallyOldtoNew();
                }
                if(result.equals("Chronologically New to Old")){
                    recipeList.SortRecipeListChronologicallyNewtoOld();
                }
            }
        };
        Sort.setOnAction(event);
        // Style string for the button
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

         // Initialize the 'Create New Recipe' button and apply style
        Create = new Button("Create New Recipe");
        Create.setStyle(defaultButtonStyle);
        this.getChildren().add(Create);
        this.getChildren().add(Sort);
        this.getChildren().add(comboBox);
        this.setAlignment(Pos.CENTER);
    }
    
    /// Method to get the Create button
    public Button getCreateButton(){
        return this.Create;
    }
}