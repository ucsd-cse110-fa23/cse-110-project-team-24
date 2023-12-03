package server;

public class ChatGPTGenerator implements RecipeGenerator{
    APIResponse api;
    
    public ChatGPTGenerator (APIResponse api) {
        this.api = api;
    }

    public Recipe generateRecipe(String mealType, String ingredients){
        String response = api.getText(mealType, ingredients);
        
        String title = getTitle(response);
        String responseIngredients = getIngredients(response);
        String instructions = getInstructions(response);

        Recipe recipe = new Recipe(title, mealType, responseIngredients, instructions, "");

        return recipe;
    }

    /**
      Assumptions:
     * assumes title within response
     * assumes title either between quotes or preceded by ":" and followed by newline
     */
     public String getTitle(String response) {
        /**
         * Source: https://www.w3schools.com/java/ref_string_indexof.asp
         * Title: Java String indexOf() Method
         * Date Accessed: 11/3/2023
         * Use: Used to understand how to get index of a character in a string from a position (use of indexOf())
         */
        int titleIndex = response.indexOf("Title:") + "Title:".length();
        int begin = titleIndex;
        while (isWhiteSpace(response.charAt(begin))) {
            begin++;
        }
        int ingredientsIndex = response.indexOf("Ingredients");
        int end = ingredientsIndex - 1;
        while (isWhiteSpace(response.charAt(end))) {
            end--;
        }
        end++;
        String titleSection = response.substring(begin, end);
        return titleSection; 
    }

    private boolean isWhiteSpace(char c) {
        boolean isNotWhiteSpace = (c > 20) && (c < 127) && (c != ' ');
        return !isNotWhiteSpace;
    }

    public String getIngredients(String response) {
        int ingredientsIndex = response.indexOf("Ingredients:");
        int start = ingredientsIndex + "Ingredients:".length();
        while (isWhiteSpace(response.charAt(start)))
            start++;

        int instructionsIndex = response.indexOf("Instructions:") - 1;
        int end = instructionsIndex;
        while (isWhiteSpace(response.charAt(end)))
            end--;
        end++;

        if (start > end)
            return "";

        String ingredientsSection = response.substring(start, end);
        return ingredientsSection;
    }

    public String getInstructions(String response) {
        int instructionsIndex = response.indexOf("Instructions:");
        int start = instructionsIndex + "Instructions:".length();

        if (start >= response.length())
            return "";

            
        while (isWhiteSpace(response.charAt(start)))
            start++;

        

        String ingredientsSection = response.substring(start);
        return ingredientsSection;
    }

    
}
