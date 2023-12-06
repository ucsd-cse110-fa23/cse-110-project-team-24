package pantrypal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerErrorView extends VBox {
    private Label errorMessage;
    private Button closeButton;

    public ServerErrorView(){
        String message = "Unable to connect to Server. Please try again later.";
        this.errorMessage = new Label(message);
        errorMessage.setPrefSize(400, 200);
        errorMessage.setAlignment(Pos.CENTER);
        errorMessage.setPadding(new Insets(10, 0, 10, 0));
        this.getChildren().add(errorMessage);

        closeButton = new Button("Close");
        closeButton.setPrefSize(100, 50);
        closeButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        closeButton.setOnAction(e -> ((Stage) this.getScene().getWindow()).close());
        this.getChildren().add(closeButton);
    }
    public Label getErrorMessage(){
        return this.errorMessage;
    }

    public static void display() {
        Stage  ServerErrorViewWindow= new Stage();
        ServerErrorView ServerErrorView = new ServerErrorView();
        Scene scene = new Scene(ServerErrorView, 300, 200);
        ServerErrorViewWindow.setScene(scene);
        ServerErrorViewWindow.setTitle("Server Error");
        ServerErrorViewWindow.show();
    }

}