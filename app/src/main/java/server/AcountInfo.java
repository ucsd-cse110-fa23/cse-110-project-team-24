package server;
import static com.mongodb.client.model.Filters.eq;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.model.*;


import static com.mongodb.client.model.Updates.*;

public class AcountInfo {

    //private String username, email, password;
    //String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> accountCollection;

    public AcountInfo(MongoCollection<Document> accountCollection) {
        this.accountCollection = accountCollection;
    }

    public void insertSingleAccount(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Username and password cannot be empty");
            return;
        }

        // Check if the username already exists
        Document existingAccount = accountCollection.find(eq("username", username)).first();
        if (existingAccount != null) {
            System.out.println("An account with this username already exists.");
            return;
        }
        else{
            Document newAccount = new Document().append("username", username).append("password", password);
            // Insert the new account into the accounts collection
            accountCollection.insertOne(newAccount);
           //System.out.println("Account created successfully for username: " + username);
        }
    }

    public void insertManyDocuments() {
        List<Document> accounts = new ArrayList<>();
        String line;
        try{
            String path = "/Users/safiamahamed/Desktop/PROJECT CSE 110/cse-110-project-team-24/app/account.csv";
            File csvfile =new File(path);
            FileReader filereader = new FileReader(csvfile);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            while((line = bufferedreader.readLine())!=null){
                String[] parts = line.split("\",\"");// use semicolons as ","
                //if(!parts[0].equalsIgnoreCase("")){
                    String accountUsername = parts[0];
                    String accountPassword = parts[1];
                    String accountEmail = parts[2];
                    accounts.add(generateNewAccount(accountUsername, accountPassword,accountEmail));  
                //}
            }  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        accountCollection.insertMany(accounts, new InsertManyOptions().ordered(false));
    }
    private Document generateNewAccount(String username, String password, String accountEmail ) {  
        Document document = new Document("_id", new ObjectId()).append("username", username) .append("password", password).append("Email", accountEmail);                        
        return document;
    }

    //Update document
    public void updateDocument(String username){
        UpdateOptions options=new UpdateOptions().upsert(true).bypassDocumentValidation(true);
        JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
        // update one document
        Bson filter = eq(username);
        Bson updateOperation = set("comment", "You should learn MongoDB!");
        UpdateResult updateResult = accountCollection.updateOne(filter, updateOperation);
        System.out.println(accountCollection.find(filter).first().toJson(prettyPrint));
        System.out.println(updateResult);
    }

    // Updates the email address of an account.
    
    public boolean updateAccountEmail(String username, String newEmailAddress) {
        if (username == null || username.isEmpty() || newEmailAddress == null || newEmailAddress.isEmpty()) {
            System.out.println("Username and new email address cannot be empty");
            return false;
        }

        Bson filter = eq("username", username);
        Bson updateOperation = set("email", newEmailAddress);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);

        return result.getModifiedCount() == 1;
    }

    public Document findAccountByUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username cannot be empty");
            return null;
        }

        Document account = accountCollection.find(eq("username", username)).first();
        return account;
    }
    public List<Document> getAllAccounts(MongoCollection<Document> accountCollection) {
        List<Document> accounts = new ArrayList<>();
        try (MongoCursor<Document> cursor = accountCollection.find().iterator()) {
            while (cursor.hasNext()) {
                accounts.add(cursor.next());
            }
        }
        return accounts;
    }
     /**
     * Updates the password for an account.
     */
    public boolean updateAccountPassword(String username, String newPassword) {
        if (username == null || username.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            System.out.println("Username and password cannot be empty");
            return false;
        }
        Bson filter = eq("username", username);
        Bson updateOperation = set("password", newPassword);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);

        return result.getModifiedCount() == 1;
    }
    
    //Updates details of an account. field updatedDocument: is a Document containing the fields to update and their new values.
    public boolean updateAccountDetails(String username, Document updatedDocument) {
        if (username == null || username.isEmpty() || updatedDocument == null) {
            System.out.println("Username and updates cannot be empty");
            return false;
        }

        Bson filter = eq("username", username);
        Bson updateOperation = new Document("set", updatedDocument);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);
        if(result.getModifiedCount() == 1){
            return true;
        }
        return false;
    }

    //Deletes an account based on the username.
     
    public boolean deleteAccountByUsername(String username) {
        if (username == null || username.isEmpty()) {
            //throw new Exception("Username cannot be empty");
            System.out.println("Username cannot be empty");
            return false;
        }
        DeleteResult result = accountCollection.deleteOne(eq("username", username));
        if(result.getDeletedCount() == 1){
            return true;
        }
        else{return false;}
        
    }

    //Need to check if the user login this the same 
    public boolean authenticateUserInfo(String username, String password) {
        Document userAccount = accountCollection.find(eq("username", username)).first();
        if (userAccount == null) {
            System.out.println("User not found.");
            return false;
        }
        else{
            String storedPassword = userAccount.getString("password");
            if (!(password.equals(storedPassword))) {
                return false; 
            } else {
                return true;
            }
        }   
    }
}

 /*
     * 
     *  
     * 
     * 
     * 
     *  
     private static boolean delete1Document(String username,String password, MongoCollection<Document> accountCollection){
    //private static void delete1Document(String username,String password){
        Bson filter = eq(username, password);
        DeleteResult result = accountCollection.deleteOne(filter);
        System.out.println(result);
        if(result == null){
            System.out.println("No documents deleted!");
            return false;
        }
        else{
            System.out.println("Deleted "+result.toString()+"documents!");
            return true;
        } //return;
    }





     public static void insertRecipe(String username, String title, String mealType, String ingredients, String steps) {
        // Links the recipe to the user AccountInfo
        Document newRecipe = new Document()
            .append("username", username)  
            .append("title", title)
            .append("mealType", mealType)
            .append("ingredients", ingredients)
            .append("steps", steps);
    
        recipeCollection.insertOne(newRecipe);
    }

    public static List<Document> getRecipesByUser(String username) {
        List<Document> userRecipes = new ArrayList<>();
        FindIterable<Document> recipes = recipeCollection.find(eq("username", username));
        for (Document recipe : recipes) {
            userRecipes.add(recipe);
        }
        return userRecipes;
    }
     */