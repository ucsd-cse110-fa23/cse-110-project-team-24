package pantrypal;

import javafx.scene.layout.VBox;

// import org.hamcrest.Condition.Step;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class GeneratedView extends VBox{
    private Button CancelButton;
    private Label Createlabel;
    private TextArea Instruction;
    private TextArea IngredientList;
    private Button SaveButton;

    GeneratedView(){

        Createlabel = new Label();
        Createlabel.setPrefSize(600, 200);
        Createlabel.setAlignment(Pos.CENTER);
        Createlabel.setPadding(new Insets(10,0,10,0));
        this.getChildren().add(Createlabel);       

        IngredientList = new TextArea();
        IngredientList.setEditable(false);
        IngredientList.setPrefSize(600, 300);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setPadding(new Insets(10,0,10,0));
        this.getChildren().add(IngredientList);

        Instruction = new TextArea();
        Instruction.setEditable(false);
        Instruction.setPrefSize(600, 200);
        Instruction.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        Instruction.setPadding(new Insets(10,0,10,0));
        this.getChildren().add(Instruction);

        CancelButton = new Button("Back");
        CancelButton.setPrefSize(600, 50);
        CancelButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        CancelButton.setAlignment(Pos.CENTER);
        this.getChildren().add(CancelButton);

        SaveButton = new Button("Save this recipe to My List");
        SaveButton.setPrefSize(600, 50);
        SaveButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        SaveButton.setAlignment(Pos.CENTER);
        this.getChildren().add(SaveButton);

    }
    public Label getCreatedLabel(){
        return this.Createlabel;
    }
    public TextArea getInstruction(){
        return this.Instruction;
    }
    public TextArea getIngredientList(){
        return this.IngredientList;
    }
    public static Scene CreateScene(GeneratedView d){
        Scene secondScene = new Scene(d, 600, 800);
        return secondScene;
    }

    // public String GetName(String ans){
    //     if(ans.contains("name")){
    //         String Name = ans.substring(ans.indexOf("name: ")+4, ans.indexOf("\n", ans.indexOf("name")+4));
    //         return Name;
    //     }
    //     return "Failed to Get Name from GPT ANSWER";
    // }

    // public String GetingredientList(String ans){
    //     if(ans.contains("ingredient")){
    //         String List = ans.substring(ans.indexOf("ingredient"), ans.indexOf("instruction"));
    //         return List;
    //     }
    //     return "Failed to Get ingredient List from GPT ANSWER";
    // }

    // public String GetInstruction(String ans){
    //     if(ans.contains("instruction")){
    //         String Instruction = ans.substring(ans.indexOf("instruction"));
    //         return Instruction;
    //     }
    //     return "Failed to Get Instruction from GPT ANSWER";
    // }

    public void OpenGeneratedView(Recipe generatedRecipe, Stage original, RecipeList taskList){
        Scene secondScene = CreateScene(this);
		// New window (Stage)
		Stage newWindow = new Stage();
        String name = generatedRecipe.getTitle();
        String IngredientLists = generatedRecipe.getIngredients();
        String StepbyStep = generatedRecipe.getSteps();
        newWindow.setTitle("Generated by AI");
        newWindow.setScene(secondScene);
        this.getCreatedLabel().setText(name);
        this.getIngredientList().setText(IngredientLists);
        this.getInstruction().setText(StepbyStep);
        CancelButton.setOnAction(e -> {
            original.close();
            newWindow.close();
        });
        SaveButton.setOnAction(e -> {
            RecipeView newrecipe = new RecipeView(generatedRecipe);
            Button titleButton = newrecipe.getTitle();            
            titleButton.setOnAction(e1 -> {
                newrecipe.OpenDetailView(newrecipe.getStage(), taskList);
            });
            Recipe receipetoAdd = newrecipe.getRecipe();


            PerformRequest.performRequest("", "PUT", receipetoAdd.getTitle() + ";" + receipetoAdd.getMealType() + ";" + receipetoAdd.getIngredients() + ";" + receipetoAdd.getSteps(), null);


            taskList.getChildren().add(0, newrecipe);
            original.close();
            newWindow.close();
        });

        newWindow.show();
    }
}
