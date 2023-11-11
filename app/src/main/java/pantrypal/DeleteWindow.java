package pantrypal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class DeleteWindow extends VBox{
    private Label Confirm;
    private Button Deleteit;
    private Button CancelDele;
    private boolean deleteconfirm = false;
    DeleteWindow() {
        Confirm = new Label("Confirm to delete this Recipe?");
        Confirm.setPrefSize(400, 150);
        Confirm.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(Confirm);

        Deleteit = new Button("Delete");
        Deleteit.setPrefSize(400, 25);
        //Deleteit.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(Deleteit);

        CancelDele = new Button("Cancel");
        CancelDele.setPrefSize(400, 25);
        //CancelDele.setPadding(new Insets(10,0,10,10));
        this.getChildren().add(CancelDele);	
    }
    public boolean isdelete(){
        return deleteconfirm;
    }
    public void setdelete(){
        deleteconfirm = true;
    }
    public static Scene CreateScene(DeleteWindow d){
        Scene secondScene = new Scene(d, 500, 250);
        return secondScene;
    }

    public void ConfirmAgain(Stage stage, RecipeView recipe, RecipeList recipeList, Stage dv){
        	

		Scene secondScene = CreateScene(this);
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Deleting it?");
		newWindow.setScene(secondScene);
        
        this.CancelDele.setOnAction(e -> {
            newWindow.close();
        });

        Deleteit.setOnAction(e -> {
            recipe.setRecipeDeleteButton();
            recipeList.removeSelectedRecipes();
            dv.close();
            newWindow.close();
                        
        });

		// Set position of second window, related to primary window.
		newWindow.setX(stage.getX() + 200);
		newWindow.setY(stage.getY() + 100);

		newWindow.show();
    }
}
