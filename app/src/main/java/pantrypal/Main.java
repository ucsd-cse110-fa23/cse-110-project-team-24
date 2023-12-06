package pantrypal;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //check if the service is available
        boolean serverAvailable = ServerStatusCheck.isServerAvailable();
        if (!serverAvailable) {
            //if Server is unavailable, show server error message
            ServerErrorView.display();
            return;
        }
        else{
            // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
            PerformRequest pr = new PerformRequest();
            AppFrame root = new AppFrame(pr);
            // Set the title of the app
            primaryStage.setTitle("PantryPal");
            // Create scene of mentioned size with the border pane
            primaryStage.setScene(new Scene(root, 700, 400));
            // Make window non-resizable
            primaryStage.setResizable(true);
            // Show the app
            primaryStage.show();
            LogInView.OpenLogInView(root);

        }
        
    }

    public static void runApplication(String[] args) {
        launch(args);
    }
}