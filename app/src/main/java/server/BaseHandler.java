package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BaseHandler implements HttpHandler {
    String uri = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Account_db");
    MongoCollection<Document> AccountCollection = sampleTrainingDB.getCollection("Account");
    MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
    private List<Recipe> recipes;
    FileRecipesCoordinator coordinator;
     String response = "Request Received";
    public BaseHandler(List<Recipe> recipes) {
        this.recipes = recipes;
        this.coordinator = new FileRecipesCoordinator(recipes);
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        //String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            } else if(method.equals("POST")){
                response = handlePost(httpExchange);
            } else if (method.equals("GET")) {
                response = handleGet(httpExchange);
            }
            else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            System.out.println(response);
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    // Add new recipe with components encoded in httpExchange request body
     String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        // Store recipe
        Recipe toAdd = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3]);
        String recipeID = recipeComponents[4];
        addRecipe(toAdd, recipeID);

        scanner.close();
        return URLEncoder.encode("Added recipe " + toAdd.toString(), "US-ASCII");
    }

    void addRecipe(Recipe toAdd, String recipeID) throws IOException {
        recipes.add(0, toAdd);
        coordinator.updateRecipes();
        Document toAddDocument = toAdd.toDocument();
        MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(recipeID);
        RecipeCollection.insertOne(toAddDocument);

    }

    // Delete recipe with componenets encoded in httpExchange URI query
     String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE Request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
    
        if (query != null) {
            String[] components = query.split(";");
            Recipe toDelete = new Recipe(components[0], components[1], components[2], components[3]);
            String RecipeID = components[4];                      
            if (deleteRecipe(toDelete, RecipeID)) {
                //return endcoded recipe
                return URLEncoder.encode("Deleted recipe " + toDelete.toString(), "US-ASCII");
            }
            response = URLEncoder.encode("Unable to find recipe " + toDelete.toString(), "US-ASCII");
        }
        return response;
    }

    boolean deleteRecipe(Recipe toDelete, String ID) throws IOException {
         MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(ID);
         Bson Filter = eq("RecipeName", toDelete.getTitle());
         RecipeCollection.findOneAndDelete(Filter);
        for (Recipe recipe:this.recipes) {
                String t1 = recipe.getTitle();
                String t2 = toDelete.getTitle();
                if (t1.equals(t2)) {
                    this.recipes.remove(recipe);
                    coordinator.updateRecipes();
                    return true;
                }
        }
        return false;
    }

    // update recipe with information encoded in request body 
     String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        scanner.close();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        Recipe toUpdate = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3]);
        String RecipeID = recipeComponents[4];
        // Update recipe
        if (updateRecipe(toUpdate, RecipeID)) {
            return URLEncoder.encode("Updated recipe to " + toUpdate.toString(), "US-ASCII");
        }

        scanner.close();
        return URLEncoder.encode("Could not find recipe " + toUpdate.toString(), "US-ASCII");


    }

    boolean updateRecipe(Recipe edited, String RecipeID) throws IOException {
        MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(RecipeID);
        List<Bson> updates = new ArrayList<>();
        Bson filter = eq("RecipeName", edited.getTitle());
        Bson update1 = set("MealType", edited.getMealType());
        updates.add(update1);
        Bson update2 = set("Ingredient List", edited.getIngredients());
         updates.add(update2);
        Bson update3 = set("Steps", edited.getSteps());
         updates.add(update3);
        RecipeCollection.findOneAndUpdate(filter, updates);

        for (int i = 0; i < recipes.size(); i++) {
            Recipe current = recipes.get(i);
            if (current.getTitle().equals(edited.getTitle())) {
                current.setIngredients(edited.getIngredients());
                current.setSteps(edited.getSteps());
                coordinator.updateRecipes();
                return true;
            }
        }
        return false;
    }

    // return delimeter separated lists of formatted recipes
    String handleGet(HttpExchange httpExchange) throws UnsupportedEncodingException {
        // String result = "";
        // for (Recipe recipe:recipes){
        //     result += recipe.toString();
        //     result += "RECIPE_SEPARATOR";
        // }
        // try {
        //     return URLEncoder.encode(result, "US-ASCII");
        // } catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        //     return null;
        // }
        String result = "";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
        response = query;
        if(query != null) {
            MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(query);
            List<Document> RecipesDocs =  RecipeCollection.find().into(new ArrayList<>());
            for(Document recipe:RecipesDocs){
                if(recipe.get("username") != null){
                    continue;
                }
                Recipe recipes  = new Recipe((String) recipe.get("RecipeName"), (String) recipe.get("MealType"), (String) recipe.get("Ingredient List"), (String) recipe.get("Steps"));
                result += recipes.toString();
                result += "RECIPE_SEPARATOR";
            }
        }
        System.out.println(result);
        return URLEncoder.encode(result, "US-ASCII");
    }

}
