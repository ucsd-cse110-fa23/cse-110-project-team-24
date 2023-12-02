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
    RecipeList recipes;

     String response = "Request Received";
    public BaseHandler(RecipeList recipes) {
        this.recipes = recipes;
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
                recipeComponents[2], recipeComponents[3], recipeComponents[4]);
        String recipeID = recipeComponents[5];
        addRecipe(toAdd, recipeID);

        scanner.close();
        return URLEncoder.encode(String.format("%d%s", 0, toAdd.toString()), "US-ASCII");
    }

    void addRecipe(Recipe toAdd, String recipeID) throws IOException {
        recipes.add(0, toAdd);
        //coordinator.updateRecipes();
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
            Recipe toDelete = new Recipe(components[0], components[1], components[2], components[3], components[4]);
            String RecipeID = components[5]; 
            int pos = deleteRecipe(toDelete, RecipeID);
            //return endcoded recipe
            return URLEncoder.encode(String.format("%d%s", pos, toDelete.toString()), "US-ASCII");

        }
        return response;
    }

    int deleteRecipe(Recipe toDelete, String ID) throws IOException {
        MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(ID);
        Bson Filter = eq("RecipeName", toDelete.getTitle());
        RecipeCollection.findOneAndDelete(Filter);
        for (int i = 0; i < this.recipes.size(); i++){
            Recipe recipe = this.recipes.get(i);
            String t1 = recipe.getTitle();
            String t2 = toDelete.getTitle();
            if (t1.equals(t2)) {
                this.recipes.remove(recipe);
                return i;
            }
        }
        return -1;
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
                recipeComponents[2], recipeComponents[3], recipeComponents[4]);
        String userID = recipeComponents[5];
        // Update recipe
        return URLEncoder.encode(String.format("%d%s", updateRecipe(toUpdate, userID), toUpdate.toString()), "US-ASCII");



    }

    int updateRecipe(Recipe edited, String RecipeID) throws IOException {
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
                return i;
            }
        }
        return -1;
    }

    // return delimeter separated lists of formatted recipes
    String handleGet(HttpExchange httpExchange) throws UnsupportedEncodingException {
        String result = "";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
        String modification = (query.split(";"))[0];
        String userID = (query.split(";"))[1];
    
        if(query != null) {
            MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(userID);
            List<Document> RecipesDocs =  RecipeCollection.find().into(new ArrayList<>());

            this.recipes.removeAll();

            for(Document recipe:RecipesDocs){
                if(recipe.get("username") != null){
                    continue;
                }

                Recipe parsedRecipe  = new Recipe((String) recipe.get("RecipeName"), 
                        (String) recipe.get("MealType"), (String) recipe.get("Ingredient List"), 
                        (String) recipe.get("Steps"), (String) recipe.get("Date"));
                this.recipes.add(parsedRecipe);
            }

            
            this.recipes.setListModifyingStrategy(modification);
            for (int i = 0; i < this.recipes.size(); i++) {
                result += recipes.get(i).toString();
                result += "RECIPE_SEPARATOR";
            }
        }
        return URLEncoder.encode(result, "US-ASCII");
    }

}
