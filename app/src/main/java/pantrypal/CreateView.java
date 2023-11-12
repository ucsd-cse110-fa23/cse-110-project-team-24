package pantrypal;

// (import statements)
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;



//CreateVie arrange its children through VBox
public class CreateView extends VBox {

     // UI components declaration
    private Button BackButton;
    private Label Createlabel;
    private Button StartRecording;
    private TextArea TypeArea;
    private TextArea IngredientList;
    private Button StartFinding;
    private Button EditButton;

    // Flag to toggle editing state
    private boolean editing = false;

    // Constructor
    CreateView() {

        // Initialize UI components with properties like size and style
        // Adding UI components to the VBox
        BackButton = new Button("Back");
        BackButton.setPrefSize(100, 50);
        BackButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        BackButton.setAlignment(Pos.CENTER);
        this.getChildren().add(BackButton);

        Createlabel = new Label("Creating new Recipe");
        Createlabel.setPrefSize(600, 200);
        Createlabel.setAlignment(Pos.CENTER);
        Createlabel.setPadding(new Insets(10, 0, 10, 0));
        this.getChildren().add(Createlabel);

        TypeArea = new TextArea();
        TypeArea.setEditable(false);
        TypeArea.setPrefSize(600, 200);
        TypeArea.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        TypeArea.setPadding(new Insets(10, 0, 10, 0));
        TypeArea.setPromptText("Type is ......");
        this.getChildren().add(TypeArea);

        IngredientList = new TextArea();
        IngredientList.setEditable(false);
        IngredientList.setPrefSize(600, 300);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setPadding(new Insets(10, 0, 10, 0));
        IngredientList.setPromptText("Your Ingredient List is ......");
        this.getChildren().add(IngredientList);

        EditButton = new Button("Edit");
        EditButton.setPrefSize(600, 100);
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        EditButton.setPadding(new Insets(0, 10, 0, 10));
        EditButton.setAlignment(Pos.CENTER);
        this.getChildren().add(EditButton);

        StartRecording = new Button("Start Record");
        StartRecording.setPrefSize(600, 100);
        StartRecording.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        StartRecording.setPadding(new Insets(0, 10, 0, 10));
        StartRecording.setAlignment(Pos.CENTER);
        this.getChildren().add(StartRecording);

        StartFinding = new Button("Create Recipe");
        StartFinding.setPrefSize(600, 100);
        StartFinding.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        StartFinding.setPadding(new Insets(0, 10, 0, 10));
        StartFinding.setAlignment(Pos.CENTER);
        this.getChildren().add(StartFinding);
    }

     // Toggles the editability of the text areas and changes button text and style
    public void SetEditable() {

         // If not currently editing, enable editing and update styles
          // If editing, disable editing and revert styles
        if (editing == false) {
            this.EditButton.setText("Editing");
            this.EditButton.setStyle("-fx-background-color: #DE3163; -fx-border-width: 0;");
            TypeArea.setEditable(true);
            IngredientList.setEditable(true);
            IngredientList.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            TypeArea.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");
            editing = true;
            return;
        }
        this.EditButton.setText("Edit");
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        editing = false;
        TypeArea.setEditable(false);
        IngredientList.setEditable(false);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        TypeArea.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
    }

    // Getter for TypeArea
    public TextArea getTypeArea() {
        return this.TypeArea;
    }

     // Getter for IngredientList
    public TextArea getIngredientList() {
        return this.IngredientList;
    }

     // Creates a new Scene with the provided CreateView instance
    public static Scene CreateScene(CreateView d) {
        Scene secondScene = new Scene(d, 600, 800);
        return secondScene;
    }

    // Creates a new Scene with the provided AudioRecorder instance
    public static Scene CreateScene(AudioRecorder auidos) {
        Scene thirdScene = new Scene(auidos, 800, 200);
        return thirdScene;
    }

    // Opens the CreateView with the ability to record, edit, and find recipes
    public void OpenCreateView(RecipeList taskList) {
        Scene secondScene = CreateScene(this);
        // New window (Stage)
        Stage createViewWindow = new Stage();
        createViewWindow.setTitle("Create Recipe");
        createViewWindow.setScene(secondScene);

        // Set action for EditButton to toggle edit mode
        this.EditButton.setOnAction(e -> {
            this.SetEditable();
        });

         // Set action for BackButton to close the current window
        this.BackButton.setOnAction(e -> {
            try {
                createViewWindow.close();
            } catch (Exception e1) {
                System.out.println("Wrong Closing");

            }
        });

         // Set action for StartRecording button to open the audio recorder
        this.StartRecording.setOnAction(e -> {
            AudioRecorder recorder = new AudioRecorder();
            Stage recorderWindow = new Stage();
            createViewWindow.setTitle("Recording your Sounds");
            Scene thirdScene = CreateScene(recorder);
            recorderWindow.setScene(thirdScene);
            recorderWindow.show();
            
            // Set up the audio recorder to handle transcription and UI updates
            recorder.setBackAndTrancriptListener(recorderWindow, this);
        });

        // Set action for StartFinding button to generate and display a recipe
        this.StartFinding.setOnAction(e -> {
            Recipe generatedRecipe = GetGeneratedRecipe(this.getTypeArea().getText(), 
                    this.getIngredientList().getText());
            GeneratedView CreatedViews = new GeneratedView();
            CreatedViews.OpenGeneratedView(generatedRecipe, createViewWindow, taskList);
        });
        createViewWindow.show();
    }

     // Generates a Recipe object from provided meal type and recipe text
    private static Recipe GetGeneratedRecipe(String mealType, String recipeText) { 
         try {

             // Send a request to generate a recipe based on the provided meal type and recipe text
            String recipe = PerformRequest.performRequest("generator/", "GET", null, mealType + ";" + recipeText);
            // Convert the response to a Recipe object and return it
            return Recipe.of(recipe);

        } catch (Exception e) {
            // Print stack trace in case of an error
            e.printStackTrace();
            //return null otherwise
            return null;
        }
    
    }
    





















//---------------- alternative approaches ----------------- //
// ---------------- saved here in case we need it -----------------//

    // public static String GetTypes(String s) {
    //     return s.substring(s.indexOf("i want a ") + 9, s.indexOf("and i have") - 1);
    // }

    // public static String GetIngredientList(String s) {
    //     String rawIngredients = s.substring(s.indexOf("and i have ") + 11);
    //     String[] ingredientList = rawIngredients.split(" ");
    //     String ans = "";
    //     for (int i = 0; i < ingredientList.length; i++) {
    //         if (ingredientList[i].contains("and")) {
    //             continue;
    //         }
    //         ans += ingredientList[i] + "\r\n";
    //     }
    //     return ans;
    // }
}
