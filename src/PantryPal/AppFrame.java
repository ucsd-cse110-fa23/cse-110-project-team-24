package PantryPal;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class AppFrame extends BorderPane{
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
            // Recipe newrecipe = new Recipe();
            // taskList.getChildren().add(newrecipe);
            // Button title = newrecipe.getTitle();
            
            // title.setOnAction(e1 -> {
            //     newrecipe.OpenDetailView(newrecipe.getStage(), taskList);
            // });
            CreateView creating = new CreateView();
            creating.OpenCreateView();
        });
    }
}
