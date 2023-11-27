package server;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import pantrypal.AppFrame;
import pantrypal.RecipeList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class RecipeSaveTest {

    @Test
    public void saveTest(){

    String Title = "Whole-Grain Egg Bake";
    String responseIngredients = "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\n" + //
            "- 4 large eggs\n" + //
            "- 2 tablespoons milk\n" + //
            "- 1/4 teaspoon salt\n" + //
            "- 1/8 teaspoon freshly ground black pepper\n" + //
            "- 1/2 cup diced onion\n" + //
            "- 1/2 cup chopped bell pepper\n" + //
            "- 1/2 cup shredded cheddar cheese\n" + //
            "- 2 tablespoons olive oil\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)\n";
    String instructions = "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\n" + //
            "\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    Recipe recipe = new Recipe(Title,  "breakfast", responseIngredients, instructions);
    
    RecipeList taskList = new RecipeList();
    try{
        taskList.saveRecipe();
    } catch (Exception e1) {
        e1.printStackTrace();
    }
    
    String queriedTitle = "";
    String queriedIngredients = "";
    String queriedInstruction = "";
    File file = new File("StoredRecipe.csv");
    try{
        FileReader filereader = new FileReader(file);
        CSVReader csvReader = new CSVReader(filereader);
        List<String[]> allRows = csvReader.readAll();
        for (String[] row : allRows) {
            queriedTitle = row[0];
            queriedIngredients = row[1];
            queriedInstruction = row[2];
        }
        csvReader.close();
    }
    catch (Exception e2) {
        e2.printStackTrace();
    assertEquals(Title, queriedTitle);
    assertEquals(responseIngredients, queriedIngredients);
    assertEquals(instructions,queriedInstruction);
    }
    //System.out.println( queriedTitle);
}
}
