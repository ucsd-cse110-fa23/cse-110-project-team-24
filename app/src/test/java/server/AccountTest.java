package server;
import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import pantrypal.Account;

public class AccountTest {
    private final String usrname1 = "RobinLi";
    private final String password1 = "1234567";

    private final String usrname2 = "AJ";
    private final String password2 = "abcdefg";

    private final String usrname3 = "Safia";
    private final String password3 = "7654321";
    ArrayList<Account> Accounts = new ArrayList<>();
    String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");

    @BeforeEach
    public void addAccounts(){
        
        Accounts.add(new Account(usrname1, password1));
        Accounts.add(new Account(usrname2, password2));
        Accounts.add(new Account(usrname3, password3));        
    }
    @Test
    public void testHandlePut(){
        for(int i = 0; i< Accounts.size(); i++){
            Accounts.get(i).InsertNewAccount();
        }
         for(int i = 0; i< Accounts.size(); i++){
             Document Account = AccountCollection.find(eq("username", Accounts.get(i).GerUsername())).first();
             assertEquals(Accounts.get(i).GerUsername(), Account.get("username"));
             assertEquals(Accounts.get(i).GerPassword(), Account.get("password"));
        }
       
    }

    @Test
    public void testCheckValid(){
        for(int i = 0; i< Accounts.size(); i++){
            Accounts.get(i).InsertNewAccount();
        }
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).CheckAccountExisted();
            assertTrue(info.equals("1"));
        }
    }
    @Test
     public void testValidWhenInvalid(){
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).CheckAccountExisted();
            assertTrue(info.equals("-1"));
        }
    }

     @Test
     public void testValidwithIncorrectPassword(){
        Account WrongUser1 = new Account(usrname1, "passs");
        WrongUser1.InsertNewAccount();
        Account WrongUser2 = new Account(usrname2, "pasdf");
        WrongUser2.InsertNewAccount();
        Account WrongUser3 = new Account(usrname3, "pasdfasasss");
        WrongUser3.InsertNewAccount();
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).CheckAccountExisted();
            assertTrue(info.equals("0"));
        }
    }

    @AfterEach
    public void ClearDataBase(){
        AccountCollection.drop();
    }
}
