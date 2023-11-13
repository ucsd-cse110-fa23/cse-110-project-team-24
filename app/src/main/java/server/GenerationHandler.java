package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GenerationHandler implements HttpHandler {

    private List<Recipe> recipes;

    public GenerationHandler(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange, new ChatGPTGenerator(new ChatGPTResponse()));
            } 
            // else if (method.equals("POST")) {
            //     response = handlePost(httpExchange);
            // } else if (method.equals("PUT")) {
            //     response = handlePut(httpExchange);
            // } else if (method.equals("DELETE")) {
            //     response = handleDelete(httpExchange);
            // } 
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

    private String handleGet(HttpExchange httpExchange, RecipeGenerator generator) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String decodedQuery = getParsedAndDecodedQuery(query);
            String[] components = decodedQuery.split(";");
            String mealType = components[0]; // Retrieve data from hashmap
            String ingredients = components[1];
            String recipe = generator.generateRecipe(mealType, ingredients).toString();
            return URLEncoder.encode(recipe, "US-ASCII");
        }
        return response;
    }


    static String getParsedAndDecodedQuery(String query) throws UnsupportedEncodingException {
        if (query == null) 
            return null;
        String parsedQuery = query.substring(query.indexOf("=") + 1);
        String decodedQuery = URLDecoder.decode(parsedQuery, "US-ASCII");
        return decodedQuery;
    }

}
