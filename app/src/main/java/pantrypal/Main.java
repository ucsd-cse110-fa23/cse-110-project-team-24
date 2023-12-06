package pantrypal;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.opencsv.CSVReader;

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
            AppFrame root = new AppFrame(pr, primaryStage);
            // Set the title of the app
            primaryStage.setTitle("PantryPal");
            // Create scene of mentioned size with the border pane
            primaryStage.setScene(new Scene(root, 700, 400));
            // Make window non-resizable
            primaryStage.setResizable(true);
            // Show the app
            primaryStage.show();
            Path storedUser = Paths.get("AutoLogIn.csv");
            if(!Files.exists(storedUser)){
            LogInView.OpenLogInView(root);
        }
        else{
            FileReader reader = new FileReader("AutoLogIn.csv");
            CSVReader csvReader = new CSVReader(reader);
            String[] line = csvReader.readNext();
            Account ExistedAccount = new Account(line[0], line[1]);
            ExistedAccount.LoadRecipeList(root, line[0]);
            root.getRecipeList().loadRecipes();
            csvReader.close();
        }

        }
        
    }

    public static void runApplication(String[] args) {
        launch(args);
    }
}