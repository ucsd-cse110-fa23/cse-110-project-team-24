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

public class AccountTest {
    private static final String usrname1 = "RobinLi";
    private static final String password1 = "1234567";

    private static final int SERVER_PORT = 8100;
    private static final String SERVER_HOSTNAME = "localhost";
    private static final String usrname2 = "AJ";
    private static final String password2 = "abcdefg";

    private static final String usrname3 = "Safia";
    private static final String password3 = "7654321";
    private  static ArrayList<Account> Accounts = new ArrayList<>();
    private static String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    private static MongoClient mongoClient = MongoClients.create(uri);
    private static MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    private static MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");
    private static HttpServer server;

    @BeforeAll
    public static void SetUpandAddAccounts() throws IOException{
        String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
        MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");
        // create a map to store data
        List<Recipe> recipes = new ArrayList<>();

        // create a server
        server = HttpServer.create(
            new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
            0);
  
        server.createContext("/generator/", new GenerationHandler(recipes));
        server.createContext("/transcript/", new TranscriptionHandler(recipes));
        server.createContext("/", new BaseHandler(recipes));
        server.createContext("/CreateAccount", new AccountHandler());
        server.createContext("/CheckAccountValid", new AccountHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
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
       AccountCollection.drop();
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
        AccountCollection.drop();
    }
    @Test
     public void testValidWhenInvalid(){
        for(int i = 0; i< Accounts.size(); i++){
            String info = Accounts.get(i).CheckAccountExisted();
            assertTrue(info.equals("-1"));
        }
        AccountCollection.drop();
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
        AccountCollection.drop();
    }

    @AfterAll
    public static void ClearDataBase(){
        server.stop(1);
        
    }
}
