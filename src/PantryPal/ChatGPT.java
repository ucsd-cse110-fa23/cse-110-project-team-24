package PantryPal;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    private static final String API_ENDPOINT = 
    "https://api.openai.com/v1/completions";
    private static final String API_KEY =
    "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";
    private static final String MODEL = "text-davinci-003";

    public  String ConductingRecipe(String type, String Ingredient) throws URISyntaxException, IOException, InterruptedException{
        //Set request parameter
        int maxTokens = 100;
        String prompt = "I want a " + type+ ". And I have" + Ingredient + ". Could you give me a recipe with following format:"+
        "name is...... ingredient list is......('-' is prohibited) Instruction is......";
       
        //int promptindex = 2;
        // while(promptindex != args.length){
        //     prompt.concat(args[promptindex]);
        //     prompt.concat(" ");
        //     promptindex++;
        // }

        //Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client


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
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");
        System.out.println(generatedText);
        return generatedText;
    }
}
