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

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class RecipeManger {

    //public  String uri = "mongodb+srv://smahamed:Iqra2014.@cluster0.h8dtnf9.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> recipeCollection;


    public RecipeManger(MongoCollection<Document> recipeCollection){
        //RecipeManger.recipeCollection = recipeCollection;
        this.recipeCollection = recipeCollection;
      
   }

   private Document generateNewRecipe(String recipeTitle, String mealType, String recipeIngredient, String recipeInstructions) {
        Document doc = new Document("_id", new ObjectId()).append("Title", recipeTitle) .append("mealType", mealType).append("Ingredients", recipeIngredient).append("Instructions",recipeInstructions);                        
        return doc;
    } 
/* 
     private Document generateUserNewRecipe(String username, String recipeTitle, String mealType, String recipeIngredient, String recipeInstructions) {
        Document doc = new Document("_id", new ObjectId()).append("username", username).append("Title", recipeTitle) .append("mealType", mealType).append("Ingredients", recipeIngredient).append("Instructions",recipeInstructions);                        
        return doc;
    } 
*/

    // associating the recipe with the user
    public void insertUserRecipe(String username, String title, String mealType, String ingredients, String instructions) {
        Document newRecipe = new Document()
            .append("username", username)
            .append("title", title)
            .append("mealType", mealType)
            .append("ingredients", ingredients)
            .append("instructions", instructions);
        recipeCollection.insertOne(newRecipe);
    }
    
    // creating/inserting a single recipe
    public void insertRecipe(String title, String mealType, String ingredients, String instructions) {
        Document newRecipe = generateNewRecipe(title, mealType, ingredients, instructions);
        recipeCollection.insertOne(newRecipe);
    }
    
    //creating/inserting multiple recipes
    private void insertManyRecipes() {
        List<Document> recipes = new ArrayList<>();
        String line;
        StringBuilder currentRecipe = new StringBuilder();   
        try {  //parsing a CSV file into BufferedReader class constructor 
            String path = "/Users/safiamahamed/Desktop/PROJECT CSE 110/cse-110-project-team-24/app/StoredRecipe.csv";
            File csvfile =new File(path);
            FileReader filereader = new FileReader(csvfile);
            try (BufferedReader bufferedreader = new BufferedReader(filereader)) {
                while ((line = bufferedreader.readLine()) != null) {  
                    if (line.startsWith("\"")) { // the loop is structured to detect the start of a new recipe with "
                        if (currentRecipe.length() > 0) {
                            String recipeEntry = currentRecipe.toString();
                           // use "," to sepereate the fields for a recipe
                            String[] parts = recipeEntry.split("\",\"");
                            //Title,mealType,Ingredients, Instructions
                            if (parts.length >= 4){
                                String recipeTitle = parts[0].replaceAll("^\"|\"$", "");
                                String recipeMealType = parts[1].replaceAll("^\"|\"$", "");
                                String recipeIngredient = parts[2].replaceAll("^\"|\"$", "");
                                String recipeInstructions = parts[3].replaceAll("^\"|\"$", "").trim();
                                recipes.add(generateNewRecipe(recipeTitle, recipeMealType, recipeIngredient,recipeInstructions));

                            }
                            // Reset for the next recipe
                            currentRecipe.setLength(0); 
                        }
                    }
                    currentRecipe.append(line).append("\n");
                }
            }
            // Process the last recipe
            if (currentRecipe.length() > 0) {
                String recipeEntry = currentRecipe.toString();
                String[] parts = recipeEntry.split("\",\"");

                if (parts.length >= 4) {
                    String recipeTitle = parts[0].replaceAll("^\"|\"$", "");
                    String recipeMealType = parts[1].replaceAll("^\"|\"$", "");
                    String recipeIngredient = parts[2].replaceAll("^\"|\"$", "");
                    String recipeInstructions = parts[3].replaceAll("^\"|\"$", "").trim();

                    recipes.add(generateNewRecipe(recipeTitle, recipeMealType, recipeIngredient, recipeInstructions));
                }
            }    
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        recipeCollection.insertMany(recipes, new InsertManyOptions().ordered(false)); 
    }  

    public Document findRecipesByTitle(String RecipeTitle){
        // find one document with Filters.eq() using the RecipeName
        Document doc = recipeCollection.find(eq("Title",RecipeTitle)).first();
        //System.out.println(" " + doc.toJson());
        return doc;
    }   
    
     public List<Document> getRecipesByUser(String username){
        List<Document> userRecipes = new ArrayList<>();
        FindIterable<Document> recipes = recipeCollection.find(eq("username", username));
        for (Document recipe : recipes) {
            userRecipes.add(recipe);
        }
        return userRecipes;
    }


     // Update a recipe
    public boolean updateRecipe(String recipeID, Document updatedDoc) {
        UpdateResult updatedRecipe = recipeCollection.updateOne(eq("_id", new ObjectId(recipeID)), new Document("set", updatedDoc));
        if(updatedRecipe.getModifiedCount() > 0){return true;}
        return false;
    }

    // Delete a recipe
    public boolean deleteRecipeByTitle(String title) {
        DeleteResult result = recipeCollection.deleteOne(eq("Title", title));
        if (result.getDeletedCount() > 0){
            return true;
        }
        return false;
    }
}

   