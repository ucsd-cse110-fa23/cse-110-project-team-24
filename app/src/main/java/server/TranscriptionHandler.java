package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TranscriptionHandler implements HttpHandler {
    private RecipeList recipes;

    public TranscriptionHandler(RecipeList recipes) {
        this.recipes = recipes;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
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
        query = query.substring(query.indexOf("=") + 1);
        
        if (query != null) {
            query = URLDecoder.decode(query, java.nio.charset.StandardCharsets.ISO_8859_1);
            byte[] fileData = query.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
            Transcription transciptor = new Whisper();
            response = transciptor.transcript(fileData);
        }

        

        // encode HTML content
        return response;
    }

}
