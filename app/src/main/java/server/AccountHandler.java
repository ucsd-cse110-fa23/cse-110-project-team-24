package server;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import pantrypal.Account;
import pantrypal.RecipeList;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import java.util.function.Consumer;


public class AccountHandler implements HttpHandler {
    String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");
    MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
    MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection("initialize");
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");        
        MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
        
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } 
            else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
       
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }
    
    private Account ExtractAccount(HttpExchange httpExchange) throws UnsupportedEncodingException{
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] AccountComponents = postData.split(";");
        scanner.close();
        return new Account(AccountComponents[0], AccountComponents[1]);
    }
    private String handleGet(HttpExchange httpExchange) throws UnsupportedEncodingException {
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
        String[] components = query.split(";");
        Account Accountinfo = new Account(components[0], components[1]);        
        Document Account = AccountCollection.find(eq("username", Accountinfo.GerUsername())).first();
        //return -1 if the account never exists;
        if(Account == null){
            return "-1";
        }
        String password = (String) Account.get("password");
        //return 1 if this account could be found in the remote database
        if(password.equals(Accountinfo.GerPassword())){
            return "1";
        }
        //return 0 if the username is correct but the password is incorrect
        return "0";
    }

    private String handlePut(HttpExchange httpExchange) throws UnsupportedEncodingException {
        Account Accountinfo = this.ExtractAccount(httpExchange);

        // Store recipe
        Document Account = new Document("RecipeList_id", Accountinfo.GerUsername());
        Account.append("username", Accountinfo.GerUsername())
               .append("password", Accountinfo.GerPassword());
        AccountCollection.insertOne(Account);
       // RecipeCollection = RecipeListDB.getCollection((String) Account.get("RecipeList_id"));
        RecipeCollection = RecipeListDB.getCollection(Accountinfo.GerUsername());
        RecipeCollection.insertOne(Account);
        //RecipeListDB.createCollection(Accountinfo.GerUsername());

        return URLEncoder.encode("Added Account " + Accountinfo.GerUsername(), "US-ASCII");
    }
    
}
