package pantrypal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

//For US 4-1 DetailView
public class DetailView extends VBox {
    private Recipe recipe;
    private Label name;
    private TextArea type;
    private TextArea IngredientList;
    private TextArea StepInstruction;
    private Button EditButton;
    private Button BackButton;
    private Button DeleteButton;
    private boolean editing = false;

    DetailView(Recipe recipe) {

        this.recipe = recipe;
        BackButton = new Button("Back");
        BackButton.setPrefSize(100, 50);
        BackButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        BackButton.setAlignment(Pos.CENTER);
        this.getChildren().add(BackButton);

        name = new Label(recipe.getTitle());
        name.setPrefSize(1400, 50);
        name.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        name.setAlignment(Pos.TOP_CENTER);
        name.setPadding(new Insets(10, 0, 10, 0));
        name.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(name);

        type = new TextArea("Type is: " + recipe.getMealType());

        type.setEditable(false);
        type.setPrefSize(1400, 100);
        // type.setAlignment(Pos.TOP_CENTER);
        type.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        type.setPadding(new Insets(10, 0, 10, 0));

        this.getChildren().add(type);

        IngredientList = new TextArea(recipe.getIngredients());
        IngredientList.setPrefSize(1400, 200);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setEditable(false);
        // IngredientList.setAlignment(Pos.CENTER);
        // IngredientList.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(IngredientList);
        IngredientList.setPadding(new Insets(10, 0, 10, 0));
        IngredientList.setEditable(false);

        StepInstruction = new TextArea(recipe.getSteps());
        StepInstruction.setPrefSize(1400, 300);
        StepInstruction.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        // StepInstruction.setAlignment(Pos.BASELINE_LEFT);
        // StepInstruction.setTextAlignment(TextAlignment.JUSTIFY);
        StepInstruction.setPadding(new Insets(10, 0, 10, 0));
        StepInstruction.setEditable(false);
        this.getChildren().add(StepInstruction);

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

    public void SetEditable(RecipeView currentRecipe) {
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

        // TODO: Actually update recipe on server and here
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
        
        this.updateRecipe(currentRecipe, IngredientList.getText(), StepInstruction.getText());

    }

    // Update RecipeView for edited recipe and change recipe on server
    private void updateRecipe(RecipeView rv, String updatedIngredients, String updatedInstructions) {
        Recipe originalRecipe = rv.getRecipe();
        originalRecipe.setIngredients(updatedIngredients);
        originalRecipe.setSteps(updatedInstructions);

        PerformRequest.performRequest("", "POST", rv.getRecipe().toString(), null);
    }

    public static Scene CreateScene(DetailView d) {
        Scene secondScene = new Scene(d, 1000, 600);
        return secondScene;
    }

    // open Detail View Window
    public void OpenView(Stage stage, RecipeView recipeView, RecipeList recipeList) {
        // StackPane secondaryLayout = new StackPane();
        // secondaryLayout.getChildren().addAll(type, IngredientList, StepInstruction);
        this.name.setText(recipeView.getRecipe().getTitle());
        Scene secondScene = CreateScene(this);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(recipe.getTitle());
        newWindow.setScene(secondScene);
        this.EditButton.setOnAction(e -> {
            this.SetEditable(recipeView);
        });

        this.BackButton.setOnAction(e -> {
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
        });

        this.DeleteButton.setOnAction(e -> {
            DeleteWindow delewin = new DeleteWindow();
            delewin.ConfirmAgain(stage, recipeView, recipeList, newWindow);
        });
        
        newWindow.setMaxHeight(800);
        newWindow.setMaxWidth(1400);
        // Set position of second window, related to primary window.
        newWindow.setX(stage.getX() + 200);
        newWindow.setY(stage.getY() + 100);
        newWindow.show();

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
}
