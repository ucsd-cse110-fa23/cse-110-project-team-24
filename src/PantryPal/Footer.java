package PantryPal;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

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