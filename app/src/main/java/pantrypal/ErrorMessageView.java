package pantrypal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class ErrorMessageView extends VBox {
    private Label ErrorMessage;
    private Button CloseButton;

    ErrorMessageView(){
        ErrorMessage = new Label();
        ErrorMessage.setPrefSize(400, 200);
        ErrorMessage.setAlignment(Pos.CENTER);
        ErrorMessage.setPadding(new Insets(10, 0, 10, 0));
        this.getChildren().add(ErrorMessage);

        CloseButton = new Button("OK");
        CloseButton.setPrefSize(400, 50);
        CloseButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        CloseButton.setAlignment(Pos.CENTER);
        this.getChildren().add(CloseButton);
    }
    public Label getErrorMessage(){
        return this.ErrorMessage;
    }

    public Button getCloseButton(){
        return this.CloseButton;
    }

    public static Scene CreateScene(ErrorMessageView d) {
        Scene secondScene = new Scene(d, 400, 250);
        return secondScene;
    }

    public static void OpenErrorMessageView(String errorMessage, boolean success, Stage LogInStage){
        ErrorMessageView errorMessageView = new ErrorMessageView();
        errorMessageView.getErrorMessage().setText(errorMessage);
        Scene secondScene = CreateScene(errorMessageView);
        // New window (Stage)
        Stage ErrorMessageViewWindow = new Stage();
        ErrorMessageViewWindow.setTitle("Error ocurred!");
        ErrorMessageViewWindow.setScene(secondScene);
        if(success){
             errorMessageView.getCloseButton().setOnAction(e -> {
            ErrorMessageViewWindow.close();
            LogInStage.close();
        });
        }
        else{
            errorMessageView.getCloseButton().setOnAction(e -> {
            ErrorMessageViewWindow.close();
        });
        }
        
        ErrorMessageViewWindow.show();
    }
}
