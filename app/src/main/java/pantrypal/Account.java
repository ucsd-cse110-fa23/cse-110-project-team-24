package pantrypal;

import java.util.ArrayList;

public class Account implements SuperAccount{
    
    private String username;
    private String password;
    public Account(String usr, String pass){
        username = usr;
        password = pass;
    }
    public String GerUsername(){
        return this.username;
    }
    public String GerPassword(){
        return this.password;
    }
    public String InsertNewAccount(){
        String info = this.CheckAccountExisted();
        if(info.equals("1")){
            //Add error message in UI
            return "Error, Account existed already, please Sign In directly";
        }
        if(info.equals("0")){
            //Add error message in UI
            return "Error, Account existed but with an incorrect password, please Sign In with correct Password";
        }
        PerformRequest.performRequest("CreateAccount", "PUT", username + ";" + password, null);
        return "Welcome to PantryPal! " + username;
    }
    public String CheckAccountExisted(){
       String info = PerformRequest.performRequest("CheckAccountValid", "GET", null, username+";"+password);
       return info;
    }
    public void LoadRecipeList(AppFrame root, String username){
        root.getRecipeList().setRecipeId(username);
        
    }
    
}
