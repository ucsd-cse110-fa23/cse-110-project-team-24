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
    
    public static void main(String[] args){
        String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
        JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase recipeDB = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> recipeCollection = recipeDB.getCollection("recipes");
            insertManyDocuments(recipeCollection);
            //recipeCollection.drop();

            MongoDatabase database = mongoClient.getDatabase("accountInfo_db");
            MongoCollection<Document> accountCollection = database.getCollection("accounts");
            insertManyDocuments(accountCollection);
            System.out.println(accountCollection.countDocuments());

           accountCollection.drop();
           recipeCollection.drop();

        }  
    }
    private static void insertSingleAccount(MongoCollection<Document> accountCollection, String username, String password) {
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
        // Create a new account document
        Document newAccount = new Document().append("username", username).append("password", password);
        // Insert the new account into the accounts collection
        accountCollection.insertOne(newAccount);
        ObjectId id = newAccount.getObjectId("_id");
        System.out.printf("Created account %s\n", id);
        // Insert the new account into the collection
        //accountCollection.insertOne(newAccount);
        //System.out.println("Account created successfully for username: " + username);

    }

    private static void insertManyDocuments(MongoCollection<Document> accountCollection) {
        List<Document> accounts = new ArrayList<>();
        String line;
        try{
            String path = "/Users/safiamahamed/Desktop/PROJECT CSE 110/cse-110-project-team-24/app/account.csv";
            File csvfile =new File(path);
            FileReader filereader = new FileReader(csvfile);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            while((line = bufferedreader.readLine())!=null){
                String[] parts = line.split(";");// use semicolons as separator 
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
    private static Document generateNewAccount(String username, String password, String accountEmail ) {  
        Document document = new Document("_id", new ObjectId()).append("username", username) .append("password", password).append("Email", accountEmail);                        
        return document;
    }

    //Update document
    public static void updateDocument(String username,MongoCollection<Document> accountCollection ){
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
    
    public static boolean updateAccountEmail(String username, String newEmailAddress, MongoCollection<Document> accountCollection) {
        if (username == null || username.isEmpty() || newEmailAddress == null || newEmailAddress.isEmpty()) {
            System.out.println("Username and new email address cannot be empty");
            return false;
        }

        Bson filter = eq("username", username);
        Bson updateOperation = set("email", newEmailAddress);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);

        return result.getModifiedCount() == 1;
    }

    public static Document findAccountByUsername(MongoCollection<Document> accountCollection, String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username cannot be empty");
            return null;
        }

        Document account = accountCollection.find(eq("username", username)).first();
        return account;
    }
    public static List<Document> getAllAccounts(MongoCollection<Document> accountCollection) {
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
    public static boolean updateAccountPassword(MongoCollection<Document> accountCollection, String username, String newPassword) {
        if (username == null || username.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            System.out.println("Username and password cannot be empty");
            return false;
        }
        Bson filter = eq("username", username);
        Bson updateOperation = set("password", newPassword);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);

        return result.getModifiedCount() == 1;
    }

    
    //Updates details of an account. field updates: is a Document containing the fields to update and their new values.
    public static boolean updateAccountDetails(MongoCollection<Document> accountCollection, String username, Document updates) {
        if (username == null || username.isEmpty() || updates == null) {
            System.out.println("Username and updates cannot be empty");
            return false;
        }

        Bson filter = eq("username", username);
        Bson updateOperation = new Document("set", updates);
        UpdateResult result = accountCollection.updateOne(filter, updateOperation);

        return result.getModifiedCount() == 1;
    }

    //Deletes an account based on the username.
     
    public static boolean deleteAccountByUsername(MongoCollection<Document> accountCollection, String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username cannot be empty");
            return false;
        }

        DeleteResult result = accountCollection.deleteOne(eq("username", username));
        return result.getDeletedCount() == 1;
    }

    public static void insertRecipe(MongoCollection<Document> recipeCollection, String username, String title, String mealType, String ingredients, String steps) {
        // Links the recipe to the user AccountInfo
        Document newRecipe = new Document()
            .append("username", username)  
            .append("title", title)
            .append("mealType", mealType)
            .append("ingredients", ingredients)
            .append("steps", steps);
    
        recipeCollection.insertOne(newRecipe);
    }

    public static List<Document> getRecipesByUser(MongoCollection<Document> recipeCollection, String username) {
        List<Document> userRecipes = new ArrayList<>();
        FindIterable<Document> recipes = recipeCollection.find(eq("username", username));
        for (Document recipe : recipes) {
            userRecipes.add(recipe);
        }
        return userRecipes;
    }

}

 /*
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
     */