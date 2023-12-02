package pantrypal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class DeleteWindow extends VBox{

    // UI components for confirmation and action buttons
    private Label Confirm;
    private Button Deleteit;
    private Button CancelDele;
    private boolean deleteconfirm = false;
    public static boolean isCreated = true;

    public DeleteWindow() {

          // Initialize confirmation label
        Confirm = new Label("Confirm to delete this Recipe?");
        Confirm.setPrefSize(400, 150);
        Confirm.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(Confirm);

        // Initialize delete button
        Deleteit = new Button("Delete");
        Deleteit.setPrefSize(400, 25);
        //Deleteit.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(Deleteit);

         // Initialize cancel button
        CancelDele = new Button("Cancel");
        CancelDele.setPrefSize(400, 25);
        //CancelDele.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(CancelDele);	
    }

     // Method to check if deletion is confirmed
    public boolean isdelete(){
        return deleteconfirm;
    }
    public Button getDeleteit(){
        return this.Deleteit;
    }
    public Button getCancelDele(){
        return this.CancelDele;
    }
     // Method to set deletion confirmation
    public void setdelete(){
        deleteconfirm = true;
    }

    // Static method to create a Scene with the DeleteWindow
    public static Scene CreateScene(DeleteWindow d){
        Scene secondScene = new Scene(d, 250, 125);
        return secondScene;
    }

    // Method to display the delete confirmation dialog and handle user actions
    public void ConfirmAgain(Stage stage, RecipeView recipe, RecipeList recipeList, Stage dv){
        	
		Scene secondScene = CreateScene(this);
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Deleting it?");
		newWindow.setScene(secondScene);
        
         // Set action for CancelDele button to close the confirmation window
        this.CancelDele.setOnAction(e -> {
            newWindow.close();
        });

         // Set action for Deleteit button to delete the recipe and close windows
        Deleteit.setOnAction(e -> {
            recipeList.getPerformRequest().performRequest("", "DELETE", null,
                recipe.getRecipe().toString() + ";" + recipeList.getRecipeId());
            dv.close();
            newWindow.close();
                        
        });

		// Set position of second window, related to primary window.
		newWindow.setX(stage.getX() + 200);
		newWindow.setY(stage.getY() + 100);

		newWindow.show();
    }
}
