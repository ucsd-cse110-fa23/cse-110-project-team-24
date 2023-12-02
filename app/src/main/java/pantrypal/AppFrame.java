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
    
    PerformRequest pr;
    private MenuButton sortByButton;

    // Constructor for AppFrame
    AppFrame(PerformRequest pr){
        this.pr = pr;
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
        this.sortByButton = footer.getSortByButton();
        
        this.Create = footer.getCreateButton();
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
            sortByButton.setText("Sort By (Currently Chronological)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Chronological" + ";" + recipeList.getRecipeId());
        });


        MenuItem alphabeticalSortingOption = new MenuItem("Alphabetical");
        sortByButton.getItems().add(alphabeticalSortingOption);
        alphabeticalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "Alphabetical"+ ";" + recipeList.getRecipeId());
        });

        MenuItem reverseChronologicalSortingOption = new MenuItem("Reverse Chronological");
        sortByButton.getItems().add(reverseChronologicalSortingOption);
        reverseChronologicalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Reverse Chronological)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseChronological"+ ";" + recipeList.getRecipeId());
        });


        MenuItem reverseAlphabeticalSortingOption = new MenuItem("Reverse Alphabetical");
        sortByButton.getItems().add(reverseAlphabeticalSortingOption);
        reverseAlphabeticalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Reverse Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseAlphabetical"+ ";" + recipeList.getRecipeId());
        });


        MenuItem noFilteringOption = new MenuItem("None");
        sortByButton.getItems().add(noFilteringOption);
        chronologicalSortingOption.setOnAction(e -> {
            sortByButton.setText("Filter By (Currently no filter)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "NoFilter" + ";" + recipeList.getRecipeId());
        });


        MenuItem breakfastFilteringOption = new MenuItem("Breakfast");
        sortByButton.getItems().add(breakfastFilteringOption);
        breakfastFilteringOption.setOnAction(e -> {
            sortByButton.setText("Filter By (Currently Breakfast)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Breakfast" + ";" + recipeList.getRecipeId());
        });

        MenuItem lunchFilteringOption = new MenuItem("Lunch");
        sortByButton.getItems().add(lunchFilteringOption);
        lunchFilteringOption.setOnAction(e -> {
            sortByButton.setText("Filter By (Currently Lunch)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Lunch" + ";" + recipeList.getRecipeId());
        });


        MenuItem dinnerFilteringOption = new MenuItem("Dinner");
        sortByButton.getItems().add(dinnerFilteringOption);
        dinnerFilteringOption.setOnAction(e -> {
            sortByButton.setText("Filter By (Currently Dinner)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Dinner" + ";" + recipeList.getRecipeId());
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

            
