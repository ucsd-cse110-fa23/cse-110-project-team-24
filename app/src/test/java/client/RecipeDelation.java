package client;


import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import pantrypal.DeleteWindow;
import pantrypal.DetailView;
import pantrypal.Recipe;
import pantrypal.RecipeView;
import pantrypal.RecipeList;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeDelation {
    private final static String EXPECTED_TITLE = "Whole-Grain Egg Bake";
    private static String EXPECTED_INGREDIENTS = "- 2 cups cooked whole-grain cereal, such as oatmeal, quinoa, or barley\n"
            + //
            "- 4 large eggs\n" + //
            "- 2 tablespoons milk\n" + //
            "- 1/4 teaspoon salt\n" + //
            "- 1/8 teaspoon freshly ground black pepper\n" + //
            "- 1/2 cup diced onion\n" + //
            "- 1/2 cup chopped bell pepper\n" + //
            "- 1/2 cup shredded cheddar cheese\n" + //
            "- 2 tablespoons olive oil\n" + //
            "- Chopped fresh herbs, such as parsley or chives (optional)";
    private static String EXPECTED_STEPS = "1. Preheat oven to 350°F. Grease an 8-inch square baking dish.\n" + //
            "\n" + //
            "2. Stir together cereal, eggs, milk, salt, and pepper in a large bowl. Fold in onion, bell pepper, cheese, and olive oil. \n"
            + //
            "\n" + //
            "3. Pour mixture into the prepared baking dish and bake for 25 to 30 minutes, until set and lightly browned on top. Sprinkle with herbs, if desired, and serve.";
    @Test
    public void DeleteWindowPopUp(){
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        Recipe expected = new Recipe(EXPECTED_TITLE, "breakfast", EXPECTED_INGREDIENTS, EXPECTED_STEPS);
        DetailView testDetailView = new DetailView(expected);
        Platform.runLater(new Runnable() {
            public void run() {
            testDetailView.getDletebutton().fire();
            assertTrue(DeleteWindow.isCreated);
            }});
        
    }
    @Test
    public void DeleteWindowConfirm(){
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        Platform.runLater(new Runnable() {
            
            public void run() {
            Recipe expected = new Recipe(EXPECTED_TITLE, "breakfast", EXPECTED_INGREDIENTS, EXPECTED_STEPS);
            RecipeView expectedView = new RecipeView(expected);
            RecipeList taskList = new RecipeList();
            taskList.getChildren().add(expectedView);         
            DeleteWindow delewin = new DeleteWindow();
                delewin.getDeleteit().fire();
                assertEquals(taskList.getChildren().get(0),null);
            }});
    }
    @Test
    public void DeleteWindowCancel(){
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        Platform.runLater(new Runnable() {
            
            public void run() {
            Recipe expected = new Recipe(EXPECTED_TITLE, "breakfast", EXPECTED_INGREDIENTS, EXPECTED_STEPS);
            RecipeView expectedView = new RecipeView(expected);
            RecipeList taskList = new RecipeList();
            taskList.getChildren().add(expectedView);            
            DeleteWindow delewin = new DeleteWindow();
                delewin.getCancelDele().fire();
                assertEquals(taskList.getChildren().get(0),expectedView);
            }});
    }
}
