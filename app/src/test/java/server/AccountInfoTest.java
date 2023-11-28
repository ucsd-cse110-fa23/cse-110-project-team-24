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
    public String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
    JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
    public MongoClient mongoClient = MongoClients.create(uri);
    public MongoDatabase accountDB = mongoClient.getDatabase("accountInfo_db"); 
    public MongoCollection<Document> accountCollection = accountDB.getCollection( "accounts");
    
    MongoDatabase recipeDB = mongoClient.getDatabase("recipes_db");
    MongoCollection<Document> recipeCollection = recipeDB.getCollection("recipes");
   
    @Test
    public void testAccountInfo(){
        // Create a new user with the username and password provided
        String username = "testUser";
        String password = "password1234567890";
        Document doc = new Document()
        .append("username", username)
        .append("password", password);
        accountCollection.insertOne(doc);
        // Check if the document was inserted correctly
        assertEquals(doc, accountCollection.find(new Document()).first());

    }
    @Test
    public void testRecipe(){
        // Create a new recipe with all fields filled out
        String title = "Beef Fried Rice";
        String mealtype = "Lunch";
        String ingredients =  "- 2 cups of cooked white or brown rice"+ //
       "- 1 lb of beef (ground or cubed)" +
        "- 1 onion, diced \n"+ //
       "- 2 cloves of garlic, minced \n" + //
       "- 2 tablespoons of sesame oil \n" + //
       "- 1/4 cup of low sodium soy sauce \n" + //
        "- 1 teaspoon of vinegar \n" + //
        "- 1/2 cup of frozen peas and carrots";
        String instructions = "1. Heat a large skillet over medium-high heat. \n" +
        //
        "\n" + //
        "2. Add the ground or cubed beef and cook until it is browned, about 5 minutes. \n" +
         //
        "\n" + //
        "3. Add the onion, garlic, sesame oil, soy sauce, and vinegar and stir to combine. \n" +
         //
        "\n" + //
        "4. Add the cooked rice and frozen peas and carrots to the pan and stir to combine.\n" +
         //
         "\n" + //
        "5. Cook until the vegetables are tender and the rice is heated through, about 8 more minutes. \n"+
         //
        "\n" + //
        "6. Serve hot. Enjoy!";

    }

        

}
