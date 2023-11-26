package server;
import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
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
import pantrypal.MockAccount;

public class AccountTest {
    private static final String usrname1 = "RobinLi";
    private static final String password1 = "1234567";


    private static final String usrname2 = "AJ";
    private static final String password2 = "abcdefg";

    private static final String usrname3 = "Safia";
    private static final String password3 = "7654321";
    private  static ArrayList<MockAccount> Accounts = new ArrayList<>();
    private static String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    private static MongoClient mongoClient = MongoClients.create(uri);
    private static MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    private static MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");


    @BeforeAll
    public static void SetUpandAddAccounts() throws IOException{
        
        Accounts.add(new MockAccount(usrname1, password1));
        Accounts.add(new MockAccount(usrname2, password2));
        Accounts.add(new MockAccount(usrname3, password3)); 
             
    }
    @Test
    public void testHandlePut(){
        for(int i = 0; i< Accounts.size(); i++){
            Accounts.get(i).InsertNewAccount();
        }
         for(int i = 0; i< Accounts.size(); i++){
             Document Account = AccountCollection.find(eq("username", Accounts.get(i).GetUsername())).first();
             assertEquals(Accounts.get(i).GetUsername(), Account.get("username"));
             assertEquals(Accounts.get(i).GetPassword(), Account.get("password"));
        }
       AccountCollection.drop();
    }

    @Test
    public void testCheckValid(){
        for(int i = 0; i< Accounts.size(); i++){
            Accounts.get(i).InsertNewAccount();
        }
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).MockCheckAccountExisted();
            assertTrue(info.equals("1"));
        }
        AccountCollection.drop();
    }
    @Test
     public void testValidWhenInvalid(){
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).MockCheckAccountExisted();
            assertTrue(info.equals("-1"));
        }
        AccountCollection.drop();
    }

     @Test
     public void testValidwithIncorrectPassword(){
        MockAccount WrongUser1 = new MockAccount(usrname1, "passs");
        WrongUser1.InsertNewAccount();
        MockAccount WrongUser2 = new MockAccount(usrname2, "pasdf");
        WrongUser2.InsertNewAccount();
        MockAccount WrongUser3 = new MockAccount(usrname3, "pasdfasasss");
        WrongUser3.InsertNewAccount();
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).MockCheckAccountExisted();
            assertTrue(info.equals("0"));
        }
        AccountCollection.drop();
    }

    @AfterAll
    public static void ClearDataBase(){
        AccountCollection.drop();
        
    }
}
