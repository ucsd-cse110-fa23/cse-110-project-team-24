package pantrypal;


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
    public void InsertNewAccount(){
        String info = this.CheckAccountExisted();
        if(info.equals("1")){
            //Add error message in UI
            return;
        }
        if(info.equals("0")){
            //Add error message in UI
            return;
        }
        PerformRequest.performRequest("CreateAccount", "PUT", username + ";" + password, null);
    }
    public String CheckAccountExisted(){
       String info = PerformRequest.performRequest("CheckAccountValid", "GET", null, username+";"+password);
       return info;
    }
    
    
}
