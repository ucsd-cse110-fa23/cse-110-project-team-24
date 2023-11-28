package pantrypal;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    private RecipeList recipeList; 
    // Button to initiate creation of a new recipe or task
    private Button Create; 
    private MenuButton sortByButton;
    

    // Constructor for AppFrame
    AppFrame(PerformRequest pr){
        header = new Header();

        // Create a tasklist Object to hold the tasks
        recipeList = new RecipeList(pr);
        // Initialise the Footer Object
        footer = new Footer();

         // Setting up a ScrollPane for the taskList for scroll function
        ScrollPane s1 = new ScrollPane(recipeList);
        s1.setFitToWidth(true);
        s1.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(s1);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        this.Create = footer.getCreateButton();
        this.sortByButton = footer.getSortByButton();
        addListeners();
    }

    // Method to add event listeners to components
    public RecipeList getRecipeList(){
        return this.recipeList;
    }
    public void addListeners(){
        Create.setOnAction(e -> {

            //creates a new CreateView instance and opens it
            CreateView createView = new CreateView();
            createView.OpenCreateView(recipeList);
        });

        MenuItem chronologicalSortingOption = new MenuItem("Chonological");
        sortByButton.getItems().add(chronologicalSortingOption);
        chronologicalSortingOption.setOnAction(e -> {
            recipeList.setSortMethod("Chronological");
            sortByButton.setText("Sort By (Currently Chronological)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "Chronological");
        });


        MenuItem alphabeticalSortingOption = new MenuItem("Alphabetical");
        sortByButton.getItems().add(alphabeticalSortingOption);
        alphabeticalSortingOption.setOnAction(e -> {
            recipeList.setSortMethod("Alphabetical");
            sortByButton.setText("Sort By (Currently Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "Alphabetical");
        });

        MenuItem reverseChronologicalSortingOption = new MenuItem("Reverse Chronological");
        sortByButton.getItems().add(reverseChronologicalSortingOption);
        reverseChronologicalSortingOption.setOnAction(e -> {
            recipeList.setSortMethod("ReverseChronological");
            sortByButton.setText("Sort By (Currently Reverse Chronological)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseChronological");
        });


        MenuItem reverseAlphabeticalSortingOption = new MenuItem("Reverse Alphabetical");
        sortByButton.getItems().add(reverseAlphabeticalSortingOption);
        reverseAlphabeticalSortingOption.setOnAction(e -> {
            recipeList.setSortMethod("ReverseAlphabetical");
            sortByButton.setText("Sort By (Currently Reverse Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseAlphabetical");
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

            
