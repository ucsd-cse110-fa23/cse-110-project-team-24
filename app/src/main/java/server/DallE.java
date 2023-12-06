package server;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;



public class DallE implements ImageGenerator {
 private static final String API_ENDPOINT ="https://api.openai.com/v1/images/generations";
 private static final String API_KEY = "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";

 private static final String MODEL = "dall-e-2";
 public static String GeneratedImage(String InputPrompt) throws IOException, InterruptedException, URISyntaxException {
    // Set request parameters
    String prompt = InputPrompt;
    int n = 1;
    // Create a request body which you will pass into request object
    JSONObject requestBody = new JSONObject();
    requestBody.put("model", MODEL);
    requestBody.put("prompt", prompt);
    requestBody.put("n", n);
    requestBody.put("size", "256x256");
    // Create the HTTP client
    HttpClient client = HttpClient.newHttpClient();
    // Create the request object
    HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();
    // Send the request and receive the response
    HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
    );
    // Process the response
    String responseBody = response.body();
    JSONObject responseJson = new JSONObject(responseBody);
   String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
   
   System.out.println("DALL-E Response:");
   System.out.println(generatedImageURL);
    return generatedImageURL;

   // Download the Generated Image to Current Directory
//    try(
//        InputStream in = new URI(generatedImageURL).toURL().openStream();
        
//    )
//    {
//        //Files.copy(in, Paths.get("image.jpg"));
//        byte[] Ans = in.readAllBytes();
//        OutputStream os = new FileOutputStream("Response.jpg"); 
//         // Starting writing the bytes in it
//             os.write(Ans);
//             os.close();
//        return Ans;
//    }
 }

}
