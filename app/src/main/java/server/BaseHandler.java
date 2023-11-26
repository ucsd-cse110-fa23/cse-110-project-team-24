package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BaseHandler implements HttpHandler {
    private RecipeList recipes;
    FileRecipesCoordinator coordinator;
    public BaseHandler(RecipeList recipes) {
        this.recipes = recipes;
        this.coordinator = new FileRecipesCoordinator(recipes);
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
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
        addRecipe(toAdd);

        scanner.close();
        return URLEncoder.encode(String.format("%d%s", 0, toAdd.toString()), "US-ASCII");
    }

    void addRecipe(Recipe toAdd) throws IOException {
        recipes.add(toAdd);
        coordinator.updateRecipes();
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
                                
            int pos = deleteRecipe(toDelete);
            return URLEncoder.encode(String.format("%d%s", pos, toDelete.toString()), "US-ASCII");
        }
        return response;
    }

    int deleteRecipe(Recipe toDelete) throws IOException {
        for (int i = 0; i < this.recipes.size(); i++){
            Recipe recipe = this.recipes.get(i);
            String t1 = recipe.getTitle();
            String t2 = toDelete.getTitle();
            if (t1.equals(t2)) {
                this.recipes.remove(recipe);
                coordinator.updateRecipes();
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
                recipeComponents[2], recipeComponents[3]);
        
        
        return URLEncoder.encode(String.format("%d%s", updateRecipe(toUpdate), toUpdate.toString()), "US-ASCII");


    }

    // Update recipe in recipe list and return position of updated recipe
    int updateRecipe(Recipe edited) throws IOException {
        for (int i = 0; i < recipes.size(); i++) {
            Recipe current = recipes.get(i);
            if (current.getTitle().equals(edited.getTitle())) {
                current.setIngredients(edited.getIngredients());
                current.setSteps(edited.getSteps());
                coordinator.updateRecipes();
                return i;
            }
        }
        return -1;
    }

    // return delimeter separated lists of formatted recipes
    String handleGet(HttpExchange httpExchange) {
        String result = "";
        for (int i = 0; i < recipes.size(); i ++) {
            result += recipes.get(i).toString();
            result += "RECIPE_SEPARATOR";
        }
        try {
            return URLEncoder.encode(result, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
