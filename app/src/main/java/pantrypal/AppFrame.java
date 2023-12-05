package pantrypal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.opencsv.exceptions.CsvException;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


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
    private MenuButton filterByButton;
    private Button LogOut;
    private Stage primaryStage;
    // Constructor for AppFrame
    AppFrame(PerformRequest pr, Stage primaryStage){
        this.primaryStage = primaryStage;
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
        this.filterByButton = footer.getFilterByButton();
        this.LogOut = footer.getLogOutButton();
        this.Create = footer.getCreateButton();
        addListeners();
    }
    
    // Method to add event listeners to components
    public RecipeList getRecipeList(){
        return this.recipeList;
    }
    public void addListeners(){
        LogOut.setOnAction(e2 -> {
            Path path = Paths.get("AutoLogIn.csv");
            if(Files.exists(path)){
                File storeduser = new File("AutoLogIn.csv");
                storeduser.delete();
                ErrorMessageView.OpenErrorMessageView("You are logged out, need to log in next time", true, this.primaryStage);
            }
        });
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
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });


        MenuItem alphabeticalSortingOption = new MenuItem("Alphabetical");
        sortByButton.getItems().add(alphabeticalSortingOption);
        alphabeticalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "Alphabetical"+ ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });

        MenuItem reverseChronologicalSortingOption = new MenuItem("Reverse Chronological");
        sortByButton.getItems().add(reverseChronologicalSortingOption);
        reverseChronologicalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Reverse Chronological)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseChronological"+ ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });


        MenuItem reverseAlphabeticalSortingOption = new MenuItem("Reverse Alphabetical");
        sortByButton.getItems().add(reverseAlphabeticalSortingOption);
        reverseAlphabeticalSortingOption.setOnAction(e -> {
            sortByButton.setText("Sort By (Currently Reverse Alphabetical)");
            recipeList.getPerformRequest().performRequest("", "GET", null, "ReverseAlphabetical"+ ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });


        MenuItem noFilteringOption = new MenuItem("None");
        filterByButton.getItems().add(noFilteringOption);
        noFilteringOption.setOnAction(e -> {
            filterByButton.setText("Filter By (Currently no filter)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "NoFilter" + ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });


        MenuItem breakfastFilteringOption = new MenuItem("Breakfast");
        filterByButton.getItems().add(breakfastFilteringOption);
        breakfastFilteringOption.setOnAction(e -> {
            filterByButton.setText("Filter By (Currently Breakfast)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Breakfast" + ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });

        MenuItem lunchFilteringOption = new MenuItem("Lunch");
        filterByButton.getItems().add(lunchFilteringOption);
        lunchFilteringOption.setOnAction(e -> {
            filterByButton.setText("Filter By (Currently Lunch)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Lunch" + ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
        });


        MenuItem dinnerFilteringOption = new MenuItem("Dinner");
        filterByButton.getItems().add(dinnerFilteringOption);
        dinnerFilteringOption.setOnAction(e -> {
            filterByButton.setText("Filter By (Currently Dinner)");
            recipeList.getPerformRequest().performRequest("", "GET", 
                    null, "Dinner" + ";" + recipeList.getRecipeId());
            for(int i = 0; i< recipeList.getChildren().size(); i++){
                if(recipeList.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) recipeList.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + recipeList.getRecipeId());
                    inside.setImage(Image);
                    Button titleButton = intask.getTitle();
                    titleButton.setOnAction(e3 -> {
                        try {
                    intask.OpenDetailView(intask.getStage(), recipeList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                    });
                }
            }
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

            
