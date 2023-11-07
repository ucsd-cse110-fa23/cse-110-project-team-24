package pantrypal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 1400, 800));
        // Make window non-resizable
        primaryStage.setResizable(true);
        // Show the app
        primaryStage.show();
        
    }

    public static void runApplication(String[] args) {
        launch(args);
    }
}