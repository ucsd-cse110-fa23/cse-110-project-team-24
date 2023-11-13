package pantrypal;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;


// Class AppFrame extends BorderPane to create a layout for the app
public class AppFrame extends BorderPane{

    // Instance variables for different components of the app
    // Header component of the app
    private Header header; 
    // Footer component of the app
    private Footer footer;
    // List to display recipes or tasks
    private RecipeList taskList; 
    // Button to initiate creation of a new recipe or task
    private Button Create; 
    

    // Constructor for AppFrame
    AppFrame(){
        header = new Header();

        // Create a tasklist Object to hold the tasks
        taskList = new RecipeList();
        try {
            taskList.loadRecipe();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        // Initialise the Footer Object
        footer = new Footer();

         // Setting up a ScrollPane for the taskList for scroll function
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

    // Method to add event listeners to components
    public RecipeList getRecipeList(){
        return this.taskList;
    }
    public void addListeners(){
        Create.setOnAction(e -> {

            //creates a new CreateView instance and opens it
            CreateView createView = new CreateView();
            createView.OpenCreateView(taskList);
        });
        
    }
}



























//---------------- alternative approaches ----------------- //
// ---------------- saved here in case we need it -----------------//

// Recipe newrecipe = new Recipe();
// taskList.getChildren().add(newrecipe);
// Button title = newrecipe.getTitle();

// title.setOnAction(e1 -> {
//     newrecipe.OpenDetailView(newrecipe.getStage(), taskList);
// });

            
