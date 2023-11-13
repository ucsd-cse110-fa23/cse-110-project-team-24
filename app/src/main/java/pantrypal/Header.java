package pantrypal;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

// Header class extends HBox to arrange its children in a horizontal row

public class Header extends HBox{
    Header() {

        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

         // Create and style the title text for the header
        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        // Add the title text to the HBox
        this.getChildren().add(titleText);
        // Align the text to the center of the HBox
        this.setAlignment(Pos.CENTER); 
    }
}
