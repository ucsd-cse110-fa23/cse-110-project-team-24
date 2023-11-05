package PantryPal;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class CreateView extends VBox {
    private Button BackButton;
    private Label Createlabel;
    private Button StartRecording;
    private TextArea TypeArea;
    private TextArea IngredientList;
    private Button StartFinding;
    private Button EditButton;
    private boolean editing = false;
    CreateView(){
        BackButton = new Button("Back");
        BackButton.setPrefSize(100, 50);
        BackButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        BackButton.setAlignment(Pos.CENTER);
        this.getChildren().add(BackButton);

        Createlabel = new Label("Creating new Recipe");
        Createlabel.setPrefSize(600, 200);
        Createlabel.setAlignment(Pos.CENTER);
        Createlabel.setPadding(new Insets(10,0,10,0));
        this.getChildren().add(Createlabel);

        TypeArea = new TextArea();
        TypeArea.setEditable(false);
        TypeArea.setPrefSize(600, 200);
        TypeArea.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        TypeArea.setPadding(new Insets(10,0,10,0));
        TypeArea.setPromptText("Type is ......");
        this.getChildren().add(TypeArea);

        IngredientList = new TextArea();
        IngredientList.setEditable(false);
        IngredientList.setPrefSize(600, 300);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        IngredientList.setPadding(new Insets(10,0,10,0));
        IngredientList.setPromptText("Your Ingredient List is ......");
        this.getChildren().add(IngredientList);

        EditButton = new Button("Edit");
        EditButton.setPrefSize(600, 100);
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        EditButton.setPadding(new Insets(0,10,0,10));
        EditButton.setAlignment(Pos.CENTER);
        this.getChildren().add(EditButton);

        StartRecording = new Button("Start Record");
        StartRecording.setPrefSize(600, 100);
        StartRecording.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        StartRecording.setPadding(new Insets(0,10,0,10));
        StartRecording.setAlignment(Pos.CENTER);
        this.getChildren().add(StartRecording);

        StartFinding = new Button("Start Find Your Recipe");
        StartFinding.setPrefSize(600, 100);
        StartFinding.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        StartFinding.setPadding(new Insets(0,10,0,10));
        StartFinding.setAlignment(Pos.CENTER);
        this.getChildren().add(StartFinding);
    }

    public void SetEditable(){
        if(editing == false){
            this.EditButton.setText("Editing");
            this.EditButton.setStyle("-fx-background-color: #DE3163; -fx-border-width: 0;");
            TypeArea.setEditable(true);           
            IngredientList.setEditable(true);
            IngredientList.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");           
            TypeArea.setStyle("-fx-background-color: #BCE29E; -fx-border-width: 0;");            
            editing = true;
            return;
        }
        this.EditButton.setText("Edit");
        EditButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        editing = false;
        TypeArea.setEditable(false);       
        IngredientList.setEditable(false);
        IngredientList.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        TypeArea.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");    
    }
    public TextArea getTypeArea(){
        return this.TypeArea;
    }
    public TextArea getIngredientList(){
        return this.IngredientList;
    }
    public static Scene CreateScene(CreateView d){
        Scene secondScene = new Scene(d, 600, 800);
        return secondScene;
    }
    public static Scene CreateScene(AudioRecorder auidos){
        Scene thirdScene = new Scene(auidos, 800, 200);
        return thirdScene;
    }
    public void OpenCreateView(){
        Scene secondScene = CreateScene(this);
		// New window (Stage)
		Stage newWindow = new Stage();
        newWindow.setTitle("Creating the recipe");
        newWindow.setScene(secondScene);
        this.EditButton.setOnAction(e -> {
            this.SetEditable();
        });
        this.BackButton.setOnAction(e -> {
            try{
                newWindow.close();
            } catch(Exception e1){
                System.out.println("Wrong Closing");
                
            }
        });

        this.StartRecording.setOnAction(e -> {
            this.recordSounds(new AudioRecorder());
        });

        this.StartFinding.setOnAction(e -> {

        });
		newWindow.show();
    }
    public void recordSounds(AudioRecorder audioRecorder){
        Scene thirdScene = CreateScene(audioRecorder);
		// New window (Stage)
		Stage newWindow = new Stage();
        newWindow.setTitle("Recording your Sounds");
        newWindow.setScene(thirdScene);
        audioRecorder.addListeners();
        newWindow.show();
        audioRecorder.getBackAndTRanscButton().setOnAction(e -> {
            newWindow.close();
            // String s = "i want a lunch, and i have carrots, eggs, and rices";
            // String type = GetTypes(s);
            // String IngredientList = GetIngredientList(s);
            // this.getTypeArea().setText(type);
            // this.getIngredientList().setText(IngredientList);
            Path p = Paths.get("recording.mp3");
            if(Files.exists(p)){
                Whisper transcript = new Whisper();
                try {
                    String s = transcript.runningTanscription().toLowerCase();
                    if(s.indexOf("i want a ") == -1 || s.indexOf("and i have") == -1){
                        this.getTypeArea().setText("Wrong Input, Please record again");
                        this.getIngredientList().setText("Wrong Input Please record again");
                    }

                    else{
                    String type = GetTypes(s);
                    String IngredientList = GetIngredientList(s);
                    this.getTypeArea().setText(type);
                    this.getIngredientList().setText(IngredientList);
                    }
                } catch (Exception e1) {
                    
                    e1.printStackTrace();
                }
            }
        });
    }
    public static String GetTypes(String s){
        return s.substring(s.indexOf("i want a ") + 9 , s.indexOf("and i have")-1);
    }
    public static String GetIngredientList(String s){
        String RawIngredient = s.substring(s.indexOf("and i have ") + 11);
        String[] ingredientLists = RawIngredient.split(" ");
        String ans = "";
        for(int i = 0; i< ingredientLists.length; i++){
            if(ingredientLists[i].contains("and")){
                continue;
            }
            ans+=ingredientLists[i] + "\r\n";
        }
        return ans;
    }
}
