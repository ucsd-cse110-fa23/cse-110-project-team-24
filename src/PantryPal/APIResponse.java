package PantryPal;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public interface APIResponse {
    public String getText(String mealType, String Ingredients);
}


class ChatGPTResponse implements APIResponse{

    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    // return recipe response generated from ChatGPT given MealType
    @Override
    public String getText (String mealType, String ingredients) { 
        String prompt = String.format("Give me a %s recipe with the ingredients %s. Put the title after \"Title:\", the ingredients after \"Ingredients:\", and the instructions after \"Instructions:\". ", mealType, ingredients); 
        

        JSONObject requestBody = createRequestBody(prompt);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = createRequest(requestBody);

        // Send the request and receive the response
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Process the response
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);

        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        return generatedText;
    }


    private JSONObject createRequestBody (String prompt) {
        // Create request body 
        JSONObject requestBody;
        requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("max_tokens", 1000);
        requestBody.put("temperature", 1.0);
        requestBody.put("prompt", prompt);
        return requestBody;
    }

    private HttpRequest createRequest(JSONObject requestBody) {
        // Create the request object
        HttpRequest request = null;
        try {
            request = HttpRequest
            .newBuilder()
            .uri(new URI(API_ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", String.format("Bearer %s", API_KEY))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return request;
    }
}



class MockAPIResponse implements APIResponse {
    String response;
    MockAPIResponse (String response) {
        this.response = response;
    }

    public String getText(String mealType, String Ingredients) {
        return this.response;
    }

}
