import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.FileChooser.ExtensionFilter;

class Recipe extends HBox{
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

class DetailView extends VBox {
    private Label name;
    private TextArea type;
    private TextArea IngredientList;
    private TextArea StepInstruction;
    private Button EditButton;
    private Button BackButton;
    private Button DeleteButton;
    private boolean editing = false;
   

    DetailView(String names, String types, String IngredientLists, String StepInstructions) {
      
        BackButton = new Button("Back");
        BackButton.setPrefSize(100, 50);
        BackButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        BackButton.setAlignment(Pos.CENTER);
        this.getChildren().add(BackButton);

        name = new Label( names);
        name.setPrefSize(1400, 50);
        name.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        name.setAlignment(Pos.TOP_CENTER);
        name.setPadding(new Insets(10, 0, 10, 0));
        name.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(name);


        type = new TextArea("Type is: " +types);
      
        type.setEditable(false);
        type.setPrefSize(1400, 100);
        //type.setAlignment(Pos.TOP_CENTER);
        type.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        type.setPadding(new Insets(10, 0, 10, 0));
        
        this.getChildren().add(type);

        IngredientList = new TextArea(IngredientLists);
        IngredientList.setPrefSize(1400, 200);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setEditable(false);
        //IngredientList.setAlignment(Pos.CENTER);
        //IngredientList.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(IngredientList);
        IngredientList.setPadding(new Insets(10, 0, 10, 0));
        IngredientList.setEditable(false);

        StepInstruction = new TextArea(StepInstructions);
        StepInstruction.setPrefSize(1400, 300);
        StepInstruction.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        //StepInstruction.setAlignment(Pos.BASELINE_LEFT);
       // StepInstruction.setTextAlignment(TextAlignment.JUSTIFY);
        StepInstruction.setPadding(new Insets(10, 0, 10, 0));
        StepInstruction.setEditable(false);
        this.getChildren().add(StepInstruction);

        EditButton = new Button("Edit");
        EditButton.setPrefSize(1400, 50);
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        EditButton.setPadding(new Insets(0,10,0,10));
        EditButton.setAlignment(Pos.CENTER);
        //this.getChildren().add(EditButton);

        DeleteButton = new Button("Delete this");
        DeleteButton.setPrefSize(1400, 50);
        DeleteButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        //DeleteButton.setPadding(new Insets(10, 0, 10, 0));
        DeleteButton.setAlignment(Pos.CENTER);
        this.getChildren().addAll(EditButton, DeleteButton);
    }
    public void SetEditable(){
        if(editing == false){
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
    
    }

    

    public static Scene CreateScene(DetailView d){
        Scene secondScene = new Scene(d, 1400, 800);
        return secondScene;
    }

    
    public void OpenView(Stage stage, String Name, Recipe recipe, RecipeList recipeList){
        // StackPane secondaryLayout = new StackPane();
		// secondaryLayout.getChildren().addAll(type, IngredientList, StepInstruction);
         this.name.setText(Name);
		Scene secondScene = CreateScene(this);

		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle(Name);
		newWindow.setScene(secondScene);
        this.EditButton.setOnAction(e -> {
            this.SetEditable();
        });
        this.BackButton.setOnAction(e -> {
            try{
                recipe.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                for (int i = 0; i < recipe.getChildren().size(); i++) {
                    recipe.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
             // change color of task to green
                }
                newWindow.close();
            } catch(Exception e1){
                System.out.println("Wrong Closing");
                
            }
        });
        this.DeleteButton.setOnAction(e ->{
            DeleteWindow delewin = new DeleteWindow();
            delewin.ConfirmAgain(stage, recipe, recipeList, newWindow);            
        });
        newWindow.setMaxHeight(800);
        newWindow.setMaxWidth(1400);
		// Set position of second window, related to primary window.
		newWindow.setX(stage.getX() + 200);
		newWindow.setY(stage.getY() + 100);
        newWindow.show();
         
        newWindow.setOnCloseRequest((e -> {
            try{
                recipe.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
                for (int i = 0; i < recipe.getChildren().size(); i++) {
                    recipe.getChildren().get(i).setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
             // change color of task to green
                }
                newWindow.close();
            } catch(Exception e1){
                System.out.println("Wrong Closing");
                
            }
        }
        ));
        
        
        
    }
}

class DeleteWindow extends HBox{
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

    public void ConfirmAgain(Stage stage, Recipe recipe, RecipeList recipeList, Stage dv){
        	

		Scene secondScene = CreateScene(this);
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Deleting it?");
		newWindow.setScene(secondScene);
        
        this.CancelDele.setOnAction(e -> {
            newWindow.close();
        });

        Deleteit.setOnAction(e -> {
            recipe.setDeleteit();
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

class RecipeList extends VBox {
    RecipeList(){
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
    public void removeSelectedRecipes() {
        this.getChildren().removeIf(task -> task instanceof Recipe && ((Recipe) task).getDeleteit());
    }
}

class Footer extends HBox {
    private Button Create;

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        Create = new Button("Create New Recipe");
        Create.setStyle(defaultButtonStyle);
        this.getChildren().add(Create);
        this.setAlignment(Pos.CENTER);
    }
    
    public Button getCreateButton(){
        return this.Create;
    }
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane{
    private Header header;
    private Footer footer;
    private RecipeList taskList;
    
    private Button Create;

    AppFrame(){
        header = new Header();

        // Create a tasklist Object to hold the tasks
        taskList = new RecipeList();
        
        // Initialise the Footer Object
        footer = new Footer();

        ScrollPane s1 = new ScrollPane(taskList);
        s1.setFitToWidth(true);
        s1.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(s1);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        this.Create = footer.getCreateButton();
        addListeners();
    }

    public void addListeners(){
        Create.setOnAction(e -> {
            Recipe newrecipe = new Recipe();
            taskList.getChildren().add(newrecipe);
            Button title = newrecipe.getTitle();
            
            title.setOnAction(e1 -> {
                newrecipe.OpenDetailView(newrecipe.getStage(), taskList);
            });
        });
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 1400, 1000));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
