package pantrypal;

import java.io.File;

//(import statements)
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

//For US 4-1 DetailView
public class DetailView extends VBox {

    // UI components and state variables
    private Recipe recipe;
    private Label name;
    private TextArea type;
    private TextArea IngredientList;
    private TextArea StepInstruction;
    private Button EditButton;
    private Button BackButton;
    private Button DeleteButton;
    private boolean editing = false;
    private ImageView RecipeImage;
    public DetailView(Recipe expected) throws IOException{

         // Initialize and style UI components
          // Add components to the VBox
        this.recipe = expected;
        BackButton = new Button("Back");
        BackButton.setPrefSize(100, 50);
        BackButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        BackButton.setAlignment(Pos.CENTER);
        this.getChildren().add(BackButton);

        name = new Label(expected.getTitle());
        name.setPrefSize(1400, 50);
        name.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        name.setAlignment(Pos.TOP_CENTER);
        name.setPadding(new Insets(10, 0, 10, 0));
        name.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(name);

        type = new TextArea("Type is: " + expected.getMealType());

        type.setEditable(false);
        type.setPrefSize(1400, 100);
        // type.setAlignment(Pos.TOP_CENTER);
        type.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        type.setPadding(new Insets(10, 0, 10, 0));

        this.getChildren().add(type);

        IngredientList = new TextArea(expected.getIngredients());
        IngredientList.setPrefSize(1400, 200);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setEditable(false);
        // IngredientList.setAlignment(Pos.CENTER);
        // IngredientList.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(IngredientList);
        IngredientList.setPadding(new Insets(10, 0, 10, 0));
        IngredientList.setEditable(false);

        StepInstruction = new TextArea(expected.getSteps());
        StepInstruction.setPrefSize(1400, 300);
        StepInstruction.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        // StepInstruction.setAlignment(Pos.BASELINE_LEFT);
        // StepInstruction.setTextAlignment(TextAlignment.JUSTIFY);
        StepInstruction.setPadding(new Insets(10, 0, 10, 0));
        StepInstruction.setEditable(false);
        this.getChildren().add(StepInstruction);
        
        RecipeImage = new ImageView();
        byte[] Ans = expected.getImage().getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
        RecipeImage.setImage(this.ByteArrayToImage(Ans));
        this.getChildren().add(RecipeImage);

        EditButton = new Button("Edit");
        EditButton.setPrefSize(1400, 50);
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        EditButton.setPadding(new Insets(0, 10, 0, 10));
        EditButton.setAlignment(Pos.CENTER);
        // this.getChildren().add(EditButton);

        DeleteButton = new Button("Delete this");
        DeleteButton.setPrefSize(1400, 50);
        DeleteButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        // DeleteButton.setPadding(new Insets(10, 0, 10, 0));
        DeleteButton.setAlignment(Pos.CENTER);
        this.getChildren().addAll(EditButton, DeleteButton);
    }
    public TextArea getType(){
        return this.type;
    }
    public Button getDletebutton(){
        return this.DeleteButton;
    }
    public TextArea getIngredient(){
        return this.IngredientList;
    }
    public Button getEditButton(){
        return this.EditButton;
    }
    public TextArea getInstruction(){
        return this.StepInstruction;
    }
    // Toggles the editability of the recipe details and updates the UI accordingly
    // Toggle edit mode and update component styles and editability
    // Update the recipe on server after editing
    public void SetEditable(RecipeView currentRecipe, RecipeList taskList) {
        if (editing == false) {
            this.EditButton.setText("Editing");
            this.EditButton.setStyle("-fx-background-color: #DE3163; -fx-border-width: 0;");
            type.setEditable(true);
            StepInstruction.setEditable(true);
            IngredientList.setEditable(true);
            IngredientList.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            StepInstruction.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            type.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            name.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            editing = true;
            return;
        }

        //Actually update recipe on server and here (TD)
        this.EditButton.setText("Edit");
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        editing = false;
        type.setEditable(false);
        StepInstruction.setEditable(false);
        IngredientList.setEditable(false);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        name.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        StepInstruction.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        type.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        
        this.updateRecipe(currentRecipe, IngredientList.getText(), StepInstruction.getText(), taskList);

    }

    // Update RecipeView for edited recipe and change recipe on server
    private void updateRecipe(RecipeView rv, String updatedIngredients, String updatedInstructions, RecipeList taskList) {
        Recipe originalRecipe = rv.getRecipe();
        originalRecipe.setIngredients(updatedIngredients);
        originalRecipe.setSteps(updatedInstructions);

        RecipeList parent = (RecipeList) rv.getParent();
        parent.getPerformRequest().performRequest("", "POST", 
                rv.getRecipe().toString()+ ";" + taskList.getRecipeId(), null);
    }

    // Creates a new Scene with the DetailView instance
    public static Scene CreateScene(DetailView d) {
        Scene secondScene = new Scene(d, 500, 800);
        return secondScene;
    }

     // Opens the DetailView in a new window and sets up actions for buttons
    public void OpenView(Stage stage, RecipeView recipeView, RecipeList recipeList) {
        // StackPane secondaryLayout = new StackPane();
        // secondaryLayout.getChildren().addAll(type, IngredientList, StepInstruction);
        this.name.setText(recipeView.getRecipe().getTitle());
        Scene secondScene = CreateScene(this);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(recipe.getTitle());
        newWindow.setScene(secondScene);

        // Set action for EditButton to enable editing mode
        this.EditButton.setOnAction(e -> {
            this.SetEditable(recipeView, recipeList);
        });

         // Set action for BackButton to close the detail view 
        this.BackButton.setOnAction(e -> {
            try {

                 // Revert style changes in the recipe view
                recipeView.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                for (int i = 0; i < recipeView.getChildren().size(); i++) {
                    recipeView.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                    // change color of task to green
                }
                newWindow.close();
            } catch (Exception e1) {
                System.out.println("Wrong Closing");
            }
        });

        // Set action for DeleteButton to open a delete confirmation window
        this.DeleteButton.setOnAction(e -> {
            DeleteWindow delewin = new DeleteWindow();
            delewin.ConfirmAgain(stage, recipeView, recipeList, newWindow);
        });
        
         // Set the maximum size for the detail view window
        newWindow.setMaxHeight(800);
        newWindow.setMaxWidth(1400);
        // Set position of second window, related to primary window.
        newWindow.setX(stage.getX() + 200);
        newWindow.setY(stage.getY() + 100);
        newWindow.show();

        // Set action for when the window is requested to close
        newWindow.setOnCloseRequest((e -> {
            try {
                recipeView.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                for (int i = 0; i < recipeView.getChildren().size(); i++) {
                    recipeView.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                    // change color of task to green
                }
                newWindow.close();
            } catch (Exception e1) {
                System.out.println("Wrong Closing");

            }
        }));

    }
    public Image ByteArrayToImage(byte[] Ans) throws IOException{
        OutputStream os = new FileOutputStream("response.jpg"); 
        // Starting writing the bytes in it
        os.write(Ans);
        os.close();
        File pic = new File("response.jpg");
        Image images = new Image(pic.toURI().toString());
        pic.delete();
        return images;
    }
}
