package pantrypal;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MockAccount implements SuperAccount{
    public String username;
    public String password;
    String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");

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
            AccountCollection.insertOne(Account);
        }
    }
    public String MockCheckAccountExisted(){
        Document Account = AccountCollection.find(eq("username", this.username)).first();
        //return -1 if the account never exists;
        if(Account == null){
            return "-1";
        }
        String password = (String) Account.get("password");
        //return 1 if this account could be found in the remote database
        if(password.equals(this.password)){
            return "1";
        }
        //return 0 if the username is correct but the password is incorrect
        return "0";
    }
}
