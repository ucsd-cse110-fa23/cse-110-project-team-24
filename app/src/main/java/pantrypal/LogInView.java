package pantrypal;

import java.io.UnsupportedEncodingException;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.beans.binding.DoubleBinding;

public class LogInView extends VBox {
    private Button CreateAccountButton;
    private Button SignInButton;
    private Button AutomaticallyLogIn;
    private TextArea UsernameInput;
    private PasswordField PasswordInput;
    //private Button ShowPassWord;

    LogInView() {  UsernameInput = new TextArea();
        UsernameInput.setEditable(false);
        UsernameInput.setPrefSize(600, 200);
        UsernameInput.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        UsernameInput.setPadding(new Insets(10, 0, 10, 0));
        UsernameInput.setPromptText("Input Your Username Here");
        UsernameInput.setEditable(true);
        this.getChildren().add(UsernameInput);

        PasswordInput = new PasswordField();
        PasswordInput.setEditable(false);
        PasswordInput.setPrefSize(600, 200);
        PasswordInput.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        PasswordInput.setPadding(new Insets(10, 0, 10, 0));
        PasswordInput.setPromptText("Input Your Password Here");
        PasswordInput.setEditable(true);
        this.getChildren().add(PasswordInput);

        
       
        CreateAccountButton = new Button("Create A New Account");
        CreateAccountButton.setPrefSize(600, 50);
        CreateAccountButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        CreateAccountButton.setAlignment(Pos.CENTER);
        this.getChildren().add(CreateAccountButton);

        SignInButton = new Button("Sign In");
        SignInButton.setPrefSize(600, 50);
        SignInButton.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        SignInButton.setAlignment(Pos.CENTER);
        this.getChildren().add(SignInButton);

        AutomaticallyLogIn = new Button("Automatically Log In");
        AutomaticallyLogIn.setPrefSize(600, 50);
        AutomaticallyLogIn.setStyle("-fx-background-color: #6495ED; -fx-border-width: 0;");
        AutomaticallyLogIn.setAlignment(Pos.CENTER);
        this.getChildren().add(AutomaticallyLogIn);

      
    }

    public TextArea getUsername(){
        return this.UsernameInput;
    }

    public PasswordField getPassword(){
        return this.PasswordInput;
    }
    public Button getCreateAccountButton(){
        return this.CreateAccountButton;
    }
    public Button getSignInButton(){
        return this.SignInButton;
    }
    public Button getAutomatically(){
        return this.AutomaticallyLogIn;
    }
    public static Scene CreateScene(LogInView d) {
        Scene secondScene = new Scene(d, 300, 225);
        return secondScene;
    }

    public static void OpenLogInView(AppFrame root){
        LogInView Initial = new LogInView();
        Scene secondScene = CreateScene(Initial);
        // New window (Stage)
        Stage LogInViewWindow = new Stage();
        LogInViewWindow.setTitle("Log In Your PantryPal");
        LogInViewWindow.setScene(secondScene);
        Initial.getCreateAccountButton().setOnAction(e -> {
            try {
                Initial.CreateAccount(LogInViewWindow, root);
            } catch (UnsupportedEncodingException e1) {
           
                e1.printStackTrace();
            }
        });
        Initial.getSignInButton().setOnAction(e -> {
            try {
                Initial.SignInAccount(LogInViewWindow, root);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        });
        Initial.getAutomatically().setOnAction(e -> {
            Initial.AutomaticallyLogIn(LogInViewWindow);
        });
        LogInViewWindow.show();

    }
    public void CreateAccount(Stage LogInViewWindow, AppFrame root) throws UnsupportedEncodingException{
        String username = this.getUsername().getText();
        String password = this.getPassword().getText();
        Account NewAccount = new Account(username, password);
        String feedback = NewAccount.InsertNewAccount();
        if(feedback.equals("Welcome to PantryPal! " + username)){
            ErrorMessageView.OpenErrorMessageView(feedback, true, LogInViewWindow);
            NewAccount.LoadRecipeList(root, username);
           
        }
        else {ErrorMessageView.OpenErrorMessageView(feedback, false, LogInViewWindow);}
    }

    public void SignInAccount(Stage LogInViewWindow, AppFrame root) throws UnsupportedEncodingException{
        String username = this.getUsername().getText();
        String password = this.getPassword().getText();
        Account ExistedAccount = new Account(username, password);
        String info = ExistedAccount.CheckAccountExisted();
        if(info.equals("1")){
            ErrorMessageView.OpenErrorMessageView("Welcome to Pantrypal! " + username, true, LogInViewWindow);
            ExistedAccount.LoadRecipeList(root, username);
            root.getRecipeList().loadRecipes();
        }
        if(info.equals("-1")){
            ErrorMessageView.OpenErrorMessageView("Failed to Sign In: Account not Existed", false, LogInViewWindow);
        }
        if(info.equals("0")){
            ErrorMessageView.OpenErrorMessageView("Failed to Sign In: Wrong Password, please try again", false, LogInViewWindow);
        }
    }

    public void AutomaticallyLogIn(Stage LogInViewWindow){
        String username = this.getUsername().getText();
        String password = this.getPassword().getText();
        Account ExistedAccount = new Account(username, password);
        String info = ExistedAccount.CheckAccountExisted();
        if(info.equals("1")){
            ErrorMessageView.OpenErrorMessageView("Welcome to Pantrypal!" + username + " You will be Automatically logged in next time!", true, LogInViewWindow);
        }
        if(info.equals("-1")){
            ErrorMessageView.OpenErrorMessageView("Failed to log in Automatically: Account not Existed", false, LogInViewWindow);
        }
        if(info.equals("0")){
            ErrorMessageView.OpenErrorMessageView("Failed to log in Automatically: Wrong Password, please try again", false, LogInViewWindow);
        }
    }
}
