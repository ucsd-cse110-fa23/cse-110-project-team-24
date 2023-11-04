package PantryPal;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.layout.*;



public class Recipe extends HBox{
    private Boolean needtodelete = false;
    private Button title;
    private Stage stage;
    private String type;
    private String IngredientList;
    private String StepInstruction;
    Recipe() {
        
        stage = new Stage();       
        this.setPrefSize(1400, 100); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        

        title = new Button("Triton Burgers");
        title.setPrefSize(1400, 100);

        
        
        title.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");

        this.getChildren().add(title);
        type = "Lunch";
        IngredientList = "Lettuce\r\n" + //
                "Tomatoes\r\n" + //
                "Cheese\r\n" + //
                "Chicken breast\r\n";
        StepInstruction = "1. Start by pounding the chicken breast to an even thickness, about 1/2 to 3/4 inch thick. This ensures that the chicken cooks evenly. \r\n" + //
                "2. Heat a grill pan or a skillet over medium-high heat and add a bit of cooking oil or non-stick cooking spray to prevent sticking.\r\n" + //
                "3. Place the chicken breast on the hot pan and cook for about 4-5 minutes on each side or until the internal temperature reaches 165°F (74°C) and the chicken is no longer pink in the center.\r\n" + //
                "4. Begin assembling your burger by placing a lettuce leaf on the bottom half of the bun. This will help keep the bun from getting soggy.\r\n" + //
                "5. Add the cooked chicken breast (with melted cheese if desired) to the lettuce.\r\n" + //
                "6. Layer tomato slices on top of the chicken.\r\n";
    }
    public boolean getDeleteit(){
        return this.needtodelete;
    }    
    public Stage getStage(){
        return this.stage;
    }
    public Button getTitle(){
        return this.title;
    }
    public String getTitleName(){
        return this.title.getText();
    }
    public void setTitleName(String RecipeName){
        this.title.setText(RecipeName);
    }
    public void setDeleteit(){
        this.needtodelete = true;
    }
    public void OpenDetailView(Stage stage, RecipeList recipeList){
        this.setStyle("-fx-border-color: #000000; -fx-border-width: 0; -fx-font-weight: bold;");
        for (int i = 0; i < this.getChildren().size(); i++) {
            this.getChildren().get(i).setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
             // change color of task to green
        }
        DetailView Viewinside = new DetailView(title.getText(), type, IngredientList, StepInstruction);
		Viewinside.OpenView(stage, this.getTitleName(), this, recipeList);
    }
}
