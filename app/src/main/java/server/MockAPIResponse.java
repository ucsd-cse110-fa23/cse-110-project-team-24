package server;

public class MockAPIResponse implements APIResponse {
    String response;
    public MockAPIResponse (String response) {
        this.response = response;
    }

    public String getText(String mealType, String Ingredients) {
        return this.response;
    }
}
