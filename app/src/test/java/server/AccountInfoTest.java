package server;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AccountInfoTest {
    public static String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
    public MongoClient mongoClient = MongoClients.create(uri);
    public MongoDatabase database = mongoClient.getDatabase("accountInfo_db"); 
    public MongoCollection<Document> accountCollection = database.getCollection( "accounts");

    @Test
    public void testAccount() throws Exception{
        //MongoClient mongoClient = MongoClients.create(uri);
        //MongoDatabase database = mongoClient.getDatabase("accountInfo_db"); 
        // MongoCollection<Document> accountCollection = database.getCollection( "accounts");

        Document doc = new Document();
        JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
        // Create a document with the data to insert into the account info collection in MongoDB
        doc.append("username", "admin")
        .append("password", "pass")
        .append("email", "admin@gmail.com");
        System.out.println(doc.toJson(settings));
        // Insert the document into the accounts collection
        accountCollection.insertOne(doc);
        // Retrieve the inserted document and print its contents
        for (Document cur : accountCollection.find()) {
            if (cur != null) {
                System.out.println("\n" + cur.toJson());
            } else {
                System.err.print("No documents found.");
            }
        }
        // Close connection to MongoDB
      //  mongoClient.close();
    }  
    /*
    @Test
    public void testLogin(){
        String username = "admin";
        String password = "pass";
        assertEquals(1, accountCollection.countDocuments(new Document("username", username)));
        Document loginDoc = accountCollection.find(new Document("username", username)).first();
        if(loginDoc == null){
            throw new RuntimeException("User not found!");
        }else{
        }
    }
     */
}
