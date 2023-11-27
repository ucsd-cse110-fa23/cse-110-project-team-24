package pantrypal;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

// Footer class extends HBox to arrange its children in a horizontal row
class Footer extends HBox {
    private Button Create;
    private MenuButton sortByButton;

    // Constructor for Footer
    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // Style string for the button
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

         // Initialize the 'Create New Recipe' button and apply style
        Create = new Button("Create New Recipe");
        Create.setStyle(defaultButtonStyle);
        this.getChildren().add(Create);
        this.setAlignment(Pos.CENTER);

        sortByButton = new MenuButton("Sort By");
        sortByButton.getItems().addAll(
                new MenuItem("Alphabetical"), new MenuItem("None"));
        this.getChildren().add(sortByButton);
    }
    
    /// Method to get the Create button
    public Button getCreateButton(){
        return this.Create;
    }

    public MenuButton getSortByButton() {
        return this.sortByButton;
    }
}