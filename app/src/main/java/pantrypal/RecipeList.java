package pantrypal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.net.URLDecoder;

public class RecipeList extends VBox implements Observer{
    private String RecipeID;
    PerformRequest pr;
    public RecipeList(PerformRequest pr) {
        this.pr = pr;
        pr.registerObserver(this);
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
        // try {
        //     //this.loadRecipes();
        // } catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        // }
    }

    public void loadRecipes() throws IOException {
        String recipeId = this.getRecipeId();
        pr.performRequest(                         
            "RecipeDataGet", "GET", 
            null, "Chronological" + ";" + recipeId);
            for(int i = 0; i< this.getChildren().size(); i++){
                if(this.getChildren().get(i) instanceof RecipeView){
                    RecipeView intask = (RecipeView) this.getChildren().get(i);
                    Recipe inside = intask.getRecipe();
                    String Image = PerformRequest.performImageRequest("Image", "POST", null, inside.getTitle()+ "IMAGE_SEP" + this.getRecipeId());
                    OutputStream os = new FileOutputStream("response.jpg"); 
                    byte[] Ans = Image.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
        // Starting writing the bytes in it
                    os.write(Ans);
                    os.close();
                    inside.setImage(Image);

                }
            }
    }

    public String getRecipeId(){
        return this.RecipeID;
    }

    public void setRecipeId(String ID){
        this.RecipeID = ID;
    }
    

    public PerformRequest getPerformRequest() {
        return pr;
    }

    // updates UI recipes
    @Override
    public void update(String method, int pos, Recipe r) {
        switch (method) {
            case "REMOVEALL":
                this.getChildren().removeAll(this.getChildren());
                break;
            case "GET":
                RecipeView newView = new RecipeView(r);
                
                Button titleButton = newView.getTitle();
                titleButton.setOnAction(e1 -> {
                    try {
                        newView.OpenDetailView(newView.getStage(), this);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                });
                this.getChildren().add(pos, newView);
                return;
            case "POST":
                RecipeView rv = (RecipeView) (this.getChildren().get(pos));
                rv.update(r);
                return;
            case "PUT":
                this.getChildren().add(pos, new RecipeView(r));
                return;
            case "DELETE":
                this.getChildren().remove(pos);
                return;
        }
    }
    

    
}