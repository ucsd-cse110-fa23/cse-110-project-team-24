package pantrypal;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

// Footer class extends HBox to arrange its children in a horizontal row
class Footer extends HBox {
    private Button Create;
    private MenuButton sortByButton;
    private MenuButton filterByButton;
    private Button LogOut;
    // Constructor for Footer
    Footer() {
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        LogOut = new Button("LogOut this Account");
        LogOut.setStyle(defaultButtonStyle);
        this.getChildren().add(LogOut);
        
        // Style string for the button
        

         // Initialize the 'Create New Recipe' button and apply style
        Create = new Button("Create New Recipe");
        Create.setStyle(defaultButtonStyle);
        this.getChildren().add(Create);
        sortByButton = new MenuButton("Sort By (Currently Chronological)");
        this.getChildren().add(sortByButton);

        filterByButton = new MenuButton("Filter By (Currently No Filter)");
        this.getChildren().add(filterByButton);

        this.setAlignment(Pos.CENTER);
    }
    
    /// Method to get the Create button
    public Button getCreateButton(){
        return this.Create;
    }
    public Button getLogOutButton(){
        return this.LogOut;
    }
    public MenuButton getSortByButton() {
        return this.sortByButton;
    }

    public MenuButton getFilterByButton() {
        return this.filterByButton;
    }
}