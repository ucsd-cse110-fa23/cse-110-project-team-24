package server;

import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;

public class RecipeManger {
   //private MongoCollection<Document> recipeCollection;

   public static void main(String[] args) {
        String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
        JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase recipeDB = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> recipeCollection = recipeDB.getCollection("recipes");
            
            insertManyDocuments(recipeCollection);
            System.out.println(recipeCollection.countDocuments());

            recipeCollection.drop();
        }
    }

   private static void insertManyDocuments(MongoCollection<Document> recipeCollection) {
        List<Document> recipes = new ArrayList<>();
        String line;   
        try {  //parsing a CSV file into BufferedReader class constructor 
            String path = "/Users/safiamahamed/Desktop/PROJECT CSE 110/cse-110-project-team-24/app/StoredRecipe.csv";
            File csvfile =new File(path);
            FileReader filereader = new FileReader(csvfile);
            BufferedReader bufferedreader = new BufferedReader(filereader) ;
            while ((line = bufferedreader.readLine()) != null) {  
                String[] parts = line.split(";");// use semicolons as separator  
                //Recipe,Description,Hours
                String recipeTitle = parts[0];
                String recipeMealType = parts[1];
                String recipeIngredient = parts[2];
                String recipeInstructions = parts[3];
                recipes.add(generateNewRecipe(recipeTitle, recipeMealType, recipeIngredient,recipeInstructions));     
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        recipeCollection.insertMany(recipes, new InsertManyOptions().ordered(false)); 
    }


    private static Document generateNewRecipe(String recipeTitle, String mealType, String recipeIngredient, String recipeInstructions) {
        Document doc = new Document("_id", new ObjectId()).append("Title", recipeTitle) .append("mealType", mealType).append("Ingredients", recipeIngredient).append("Instructions",recipeInstructions);                        
        return doc;
    }   

    public static Document findRecipesByTitle(String RecipeName, MongoCollection<Document> recipeCollection){
        // find one document with Filters.eq() using the RecipeName
        Document doc = recipeCollection.find(eq("Recipe",RecipeName)).first();
        //System.out.println(" " + doc.toJson());
        return doc;
    }    
}
