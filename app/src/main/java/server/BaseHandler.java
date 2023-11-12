package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BaseHandler implements HttpHandler {
    private List<Recipe> recipes;

    public BaseHandler(List<Recipe> recipes) {
        this.recipes = recipes;
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

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        // Store recipe
        Recipe toAdd = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3]);
        recipes.add(0, toAdd);

        scanner.close();
        return URLEncoder.encode("Added recipe " + toAdd.toString(), "US-ASCII");
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE Request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
    
        if (query != null) {
            String[] components = query.split(";");
            Recipe toDelete = new Recipe(components[0], components[1], components[2], components[3]);
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

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        postData = URLDecoder.decode(postData, "US-ASCII");
        String[] recipeComponents = postData.split(";");

        Recipe toUpdate = new Recipe(recipeComponents[0], recipeComponents[1], 
                recipeComponents[2], recipeComponents[3]);
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

}
