package pantrypal;

public interface RecipeGenerator {
    public Recipe generateRecipe(String mealType, String Ingredients);
}
// Generate what the api is going to give us.
class ChatGPTGenerator implements RecipeGenerator{ // get what the RecipeParser 
    APIResponse api;
    
    public ChatGPTGenerator (APIResponse api) {
        this.api = api;
    }

    public Recipe generateRecipe(String mealType, String ingredients){
        String response = api.getText(mealType, ingredients);
        
        String title = getTitle(response);
        String responseIngredients = getIngredients(response);
        String instructions = getInstructions(response);

        Recipe recipe = new Recipe(title, mealType, responseIngredients, instructions);

        return recipe;
    }

    /**
      Assumptions:
     * assumes title within response
     * assumes title either between quotes or preceded by ":" and followed by newline
     */
     String getTitle(String response) {
        /**
         * Source: https://www.w3schools.com/java/ref_string_indexof.asp
         * Title: Java String indexOf() Method
         * Date Accessed: 11/3/2023
         * Use: Used to understand how to get index of a character in a string from a position (use of indexOf())
         */
        

        // int colonIndex = response.indexOf("\r\n"); 
        // String title = response.substring(0, colonIndex);
        // return title;
        int labelLength = 8;
        String titleSection = response.substring(labelLength + 1, response.indexOf("Ingredients") - 2);
        return titleSection; 
    }

    String getIngredients(String response) {
    //     String firstDelimeter = "Ingredients";
    //     String secondDelimeter = "Instructions"; 
    //     int instructionsPointer = response.indexOf(secondDelimeter);

    //    String ingredients = response.substring(response.indexOf(firstDelimeter) +14, // the number of characters in the firstDelimeter and \r\n 
    //            instructionsPointer - 2);
    //     return ingredients;
        int ingredientsStart = response.indexOf("Ingredients:") + 13;
        int ingredentsEnd = response.indexOf("Instructions:") - 1;
        String ingredientsSection = response.substring(ingredientsStart, ingredentsEnd);
        return ingredientsSection;
    }

    String getInstructions(String response) {
        // String firstDelimeter = "Instructions";
        // String ingredients = response.substring(response.indexOf(firstDelimeter)+ 15); // // the number of characters in the firstDelimeter and \r\n 
        // return ingredients; 
        int instructionsStart = response.indexOf("Instructions:") + 14;
        String ingredientsSection = response.substring(instructionsStart);
        return ingredientsSection;
    }

    
}
