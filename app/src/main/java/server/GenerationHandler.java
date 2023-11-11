package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GenerationHandler implements HttpHandler {

    private List recipes;

    public GenerationHandler(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
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

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String[] parsedQuery = query.substring(query.indexOf("=") + 1).split(",");
            String mealType = parsedQuery[0]; // Retrieve data from hashmap
            String ingredients = parsedQuery[1];
            APIResponse api = new ChatGPTResponse();
            RecipeGenerator generator = new ChatGPTGenerator(api);
            return generator.generateRecipe(mealType, ingredients).toString();
        }
        return response;
    }

    // private String handlePost(HttpExchange httpExchange) throws IOException {
    //     InputStream inStream = httpExchange.getRequestBody();
    //     Scanner scanner = new Scanner(inStream);
    //     String postData = scanner.nextLine();
    //     String language = postData.substring(
    //             0,
    //             postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);

    //     // Store data in hashmap
    //     data.put(language, year);

    //     String response = "Posted entry {" + language + ", " + year + "}";
    //     System.out.println(response);
    //     scanner.close();

    //     return response;
    // }

    // private String handlePut(HttpExchange httpExchange) throws IOException {
    //     InputStream inStream = httpExchange.getRequestBody();
    //     Scanner scanner = new Scanner(inStream);
    //     String postData = scanner.nextLine();
    //     String language = postData.substring(
    //             0,
    //             postData.indexOf(",")), year = postData.substring(postData.indexOf(",") + 1);

    //     // Store data in hashmap
    //     String response;
    //     String previous = null;
    //     if ((previous = data.put(language, year)) == null) {
    //         response = "Added entry {" + language + ", " + year + "}";
    //         System.out.println(response);
    //     } else {
    //         response = String.format("Updated entry {%s, %s} (previous year: %s)",
    //                 language, year, previous);
    //         System.out.println(response);
    //     }

    //     scanner.close();
    //     return response;
    // }

    // private String handleDelete(HttpExchange httpExchange) throws IOException {
    //     String response = "Invalid DELETE Request";
    //     URI uri = httpExchange.getRequestURI();
    //     String query = uri.getRawQuery();
    
    //     if (query != null) {
    //         String value = query.substring(query.indexOf("=") + 1);
    //         String year = data.remove(value); // Retrieve data from hashmap
            
    //         if (year != null) {
    //             response = "Deleted entry {" + value + ", " + year + "}";
    //             System.out.println(response);
    //         } else {
    //             response = "No data found for " + value;
    //         }
    //     }
    //     return response;
    // }

}
