package PantryPal;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;


//For US 4-3
public class Recipe extends HBox{
    private Boolean needtodelete = false;
    private Button title;
    private Stage stage;
    private String type;
    private String IngredientList;
    private String StepInstruction;
    Recipe(String Name, String Type, String ingredientList, String Instruction) {
        
        stage = new Stage();       
        this.setPrefSize(1400, 100); // sets size of contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task
        

        title = new Button(Name);
        title.setPrefSize(1400, 100);      
        title.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        this.getChildren().add(title);

        type = Type;
        IngredientList = ingredientList;
        StepInstruction = Instruction;
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
