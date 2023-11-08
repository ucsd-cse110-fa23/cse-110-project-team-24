/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pantrypal;

import org.junit.jupiter.api.Test;

import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

public class VoiceInputTest {

    @Test
    public void testCreationWithTranscription() {
        String recipe = "Title:   Recipe Title\n\nIngredients:\nOne Ingredient\nSecond Ingredient\nInstructions:\nSome step\nAnother easy step\nThird step";
        String mealType = "dinner";
        String title = "Recipe Title";
        String ingredients = "One Ingredient\nSecond Ingredient";
        String instructions = "Some step\nAnother easy step\nThird step";
        Transcription t = new MockTranscription(recipe);
        String transcription  = t.transcript();
        RecipeGenerator generator = new ChatGPTGenerator(new MockAPIResponse(transcription));
        Recipe result = generator.generateRecipe(mealType, transcription);

        Recipe expected = new Recipe(title, mealType, ingredients, instructions);
        assertEquals(true, expected.equals(result));
    }
}