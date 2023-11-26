package pantrypal;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MockAccount implements SuperAccount{
    public static ArrayList<Document> Mockdatabase = new ArrayList<>();
    public String username;
    public String password;
   

    public MockAccount(String usr, String pass) {
       username = usr;
       password = pass;
    }
    public String GetUsername(){
        return username;
    }
    public String GetPassword(){
        return password;
    }
    public void InsertNewAccount(){
        Document Account = new Document("_id", new ObjectId());
        Account.append("username", this.username)
            .append("password", this.password);
        if(this.MockCheckAccountExisted().equals("-1")){
           Mockdatabase.add(Account);
        }
    }
    public String MockCheckAccountExisted(){
        for(int i = 0; i< Mockdatabase.size();i++){
            if(Mockdatabase.get(i).get("username").equals(username)){
                Document Account = Mockdatabase.get(i);                
                String password = (String) Account.get("password");
                //return 1 if this account could be found in the remote database
                if(password.equals(this.password)){
                    return "1";
                }
                //return 0 if the username is correct but the password is incorrect
                return "0";
            }
        }
        return "-1";
       
    }
}
