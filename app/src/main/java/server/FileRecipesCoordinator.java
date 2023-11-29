package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import javafx.scene.control.Button;
import pantrypal.RecipeView;

public class FileRecipesCoordinator {
    List<Recipe> recipes;

    FileRecipesCoordinator(List<Recipe> coordinatedRecipes) {
        this.recipes = coordinatedRecipes;
        try {
            this.recipes.addAll(this.readRecipes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> readRecipes() throws IOException, CsvException {
        List<Recipe> result = new ArrayList<Recipe>();
        File file = new File("StoredRecipe.csv");
        FileReader filereader = new FileReader(file);
        CSVReader csvReader = new CSVReader(filereader);
        List<String[]> allRows = csvReader.readAll();
        for (String[] row : allRows) {
            Recipe stored = new Recipe(row[0], row[1], row[2], row[3], row[4]);
            result.add(result.size(), stored);
        }
        csvReader.close();

        return result;
    }

    public void updateRecipes() throws IOException {
        File file = new File("StoredRecipe.csv");
        String[] row = new String[4];
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        for (Recipe recipe:recipes) {
            row[0] = recipe.getTitle();
            row[1] = recipe.getMealType();
            row[2] = recipe.getIngredients();
            row[3] = recipe.getSteps();
            row[4] = recipe.getDate();
            csvWriter.writeNext(row);
        }
        csvWriter.close();
    }
}
