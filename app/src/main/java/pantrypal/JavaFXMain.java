package pantrypal;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*
 * Separate class since class with main extending Application throws runtime error
 */
public class JavaFXMain {

    public static void main(String[] args) {
       
        
        Main.runApplication(args);

        // System.out.println(
        //         PerformRequest.performRequest("", "PUT",
        //                 "RecipeTilte;RecipeType;RecipeIngredients;RecipeSteps", null));
        // System.out.println(
        //         PerformRequest.performRequest("", "POST", 
        //         "RecipeTilte;RecipeType;RecipeIngredients;RecipeSTEPS", null));
        // System.out.println(PerformRequest.performRequest("",
        //         "DELETE", null,
        //         "RecipeTilte;RecipeType;RecipeIngredients;RecipeSTEPS"));
    }
}