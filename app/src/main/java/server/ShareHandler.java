package server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.*;

import javafx.scene.image.Image;

import static com.mongodb.client.model.Filters.eq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.bson.Document;


public class ShareHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "ShareHandler Request Received";
        String method = exchange.getRequestMethod();
        if(method.equals("GET")) {
            response = handleGet(exchange);
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException{
        String response  = "Invalid Get Request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        query = query.substring(query.indexOf("=") + 1);
        query = URLDecoder.decode(query, "US-ASCII");
        String[] components = query.split(";");
        {
            String uris = "mongodb+srv://Robin:Ltq2021f123@cluster0.6iivynp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(uris);
            MongoDatabase RecipeListDB = mongoClient.getDatabase("Recipe_db");
            String Title = components[0];
            String MealType = components[1];
            String IngredientList = components[2];
            String StepInstruction = components[3];
            String recipeID = components[5];
            MongoCollection<Document> RecipeCollection = RecipeListDB.getCollection(recipeID);
            Document Selected_recipe = RecipeCollection.find(eq("RecipeName", Title)).first();
            String Image = (String) Selected_recipe.get("Image");
            StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
    //     .append("<html>")
    //    .append("<body>")
    //    .append("<h1>")
    //    .append("Hello ")
    //    .append(components[0])
    //    .append("</h1>")
    //    .append("</body>")
    //    .append("</html>");
        .append("<html>")
        .append("<body>")
        .append("<h1>")
        .append("Recipe Name:")
        .append(Title)
        .append("<br/>")
        .append("<br/>")
        .append("Meal Type:")
        .append(MealType)
        .append("<br/>")
        .append("<br/>")
        .append("Ingredient List:")
        .append(IngredientList)
        .append("<br/>")
        .append("<br/>")
        .append("Instructions step by step:")
        .append(StepInstruction)
        .append("<br/>")
        .append("<br/>")
        .append("<img ")
        .append("src=")
        .append(Image + " ")
        .append("alt= recipe")
        .append(">")
        .append("</h1>")
        .append("</body>")
        .append("</html>");

        
     // encode HTML content
        response = htmlBuilder.toString();
        System.out.println(response);
        return response;
        }
    }

     public URI ByteArrayToImage(byte[] Ans) throws IOException{
        OutputStream os = new FileOutputStream("response.jpg"); 
        // Starting writing the bytes in it
        os.write(Ans);
        os.close();
        File pic = new File("response.jpg");
        URI uri = pic.toURI();
        return uri;
    }
}