package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// MockBaseHandler class handles HTTP requests related to recipes
public class MockBaseHandler implements HttpHandler {
    private List<Recipe> recipes;
    
    public MockBaseHandler(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    // Handle the incoming HTTP request
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
    // Handle PUT request: Add a new recipe to the list
    public String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        // Store recipe
        Recipe toAdd = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3], recipeComponents[4]);
        recipes.add(0, toAdd);

        scanner.close();
        return URLEncoder.encode("Added recipe " + toAdd.toString(), "US-ASCII");
    }
    // Handle DELETE request: Remove a recipe from the list
    public String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE Request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");

        if (query != null) {
            String[] components = query.split(";");
            Recipe toDelete = new Recipe(components[0], components[1], components[2], components[3], components[4]);
            for (Recipe recipe:this.recipes) {
                String t1 = recipe.getTitle();
                String t2 = toDelete.getTitle();
                if (t1.equals(t2)) {
                    this.recipes.remove(recipe);
                    return URLEncoder.encode("Deleted recipe " + recipe.toString(), "US-ASCII");
                }
            }
            response = URLEncoder.encode("Unable to find recipe " + toDelete.toString(), "US-ASCII");

        }
        return response;
    }
    // Handle POST request: Update an existing recipe in the list
    public String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        Recipe toUpdate = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3], recipeComponents[4]);
        // Update recipe
        for (int i = 0; i < recipes.size(); i++) {
            Recipe current = recipes.get(i);
            if (current.getTitle().equals(toUpdate.getTitle())) {
                current.setIngredients(toUpdate.getIngredients());
                current.setSteps(toUpdate.getSteps());
                return URLEncoder.encode("Updated recipe to " + toUpdate.toString(), "US-ASCII");
            }
        }

        scanner.close();
        return URLEncoder.encode("Could not find recipe " + toUpdate.toString(), "US-ASCII");


    }
    // Handle GET request: Retrieve all recipes in the list
    public String handleGet(HttpExchange httpExchange) {
        String result = "";
        for (Recipe recipe:recipes){
            result += recipe.toString();
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