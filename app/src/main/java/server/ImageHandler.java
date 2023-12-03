package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.*;
import com.sun.prism.Image;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;

public class ImageHandler implements HttpHandler {
    HashMap<String, String> ImageStore;
   

    public ImageHandler() {
       ImageStore = new HashMap<>();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange, new DallE());
            } 
            else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } 
             else if (method.equals("POST")) {
                response = handleLoad(httpExchange);
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
        outStream.write(response.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
        outStream.close();

    }

    private String handleGet(HttpExchange httpExchange, DallE ImageGenerator) throws IOException, InterruptedException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String decodedQuery = getParsedAndDecodedQuery(query);
           
            byte[] image = DallE.GeneratedImage(decodedQuery);
            
            String ans = new String(image, java.nio.charset.StandardCharsets.ISO_8859_1);
            ImageStore.put(query, ans);
            System.out.println(ans.length());
            byte[] decode = ans.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
            OutputStream os = new FileOutputStream("responseInServer.jpg"); 
        // Starting writing the bytes in it
            os.write(decode);
            os.close();
            //return result.toString();
            return URLEncoder.encode(ans, java.nio.charset.StandardCharsets.ISO_8859_1);
        }
        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String decodedQuery = getNODATALOSSParsedAndDecodedQuery(query);
            String[] component = decodedQuery.split("IMAGE_SEP");
            String image = component[0];
            String name = component[1];
            String recipeID = component[2];
            String uris = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(uris);
            MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
            MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(recipeID);
            Bson Filter = eq("RecipeName", name);
            Bson Update = set("Image", image);
            RecipeCollection.findOneAndUpdate(Filter, Update);
            System.out.println("Image Uploaded");
        }
        return response;
    }

    private String handleLoad(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String decodedQuery = getNODATALOSSParsedAndDecodedQuery(query);
            String[] component = decodedQuery.split("IMAGE_SEP");
            String name = component[0];
            String recipeID = component[1];
            String uris = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(uris);
            MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
            MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(recipeID);
            Bson Filter = eq("RecipeName", name);
            Document recipeInServer = RecipeCollection.find(Filter).first();
            ImageStore.put(name, (String) recipeInServer.get("Image"));
            System.out.println("Recipe Founded and return Image");
            return URLEncoder.encode((String) recipeInServer.get("Image"), java.nio.charset.StandardCharsets.ISO_8859_1);
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

    static String getNODATALOSSParsedAndDecodedQuery(String query) throws UnsupportedEncodingException {
        if (query == null) 
            return null;
        String parsedQuery = query.substring(query.indexOf("=") + 1);
        String decodedQuery = URLDecoder.decode(parsedQuery, java.nio.charset.StandardCharsets.ISO_8859_1);
        return decodedQuery;
    }

}
