package pantrypal;

import java.time.ZonedDateTime;

import org.bson.Document;

public class Recipe {
    private final String BREAKFAST = "Breakfast";
    private final String LUNCH = "Lunch";
    private final String DINNER = "Dinner";
     // Class attributes for recipe details
    String title;
    String mealType;
    String ingredients;
    String steps;
    String  datetime;
    String image;
    // Constructor to create a new Recipe instance
    public Recipe (String title, String mealType, String ingredients, String steps, String Date, String imageString) {
        this.title = title;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
        this.datetime = Date;
        this.image = imageString;
    }  
    public Recipe (String title, String mealType, String ingredients, String steps, String Date) {
        this.title = title;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
        this.datetime = Date;
    }  
    public void setDate(String Date){
        this.datetime = Date;
    }
    public void setImage(String imageString){
        this.image = imageString;
    }
    public String getDate(){
        return this.datetime;
    }
    // Static method to create a Recipe instance from a string representation
    // returns Recipe based on string representation with format title;mealType;ingredients;steps
    public static Recipe of (String representation) {
        // Split the string representation into its components
        String[] components = representation.split(";");
        // Create and return a new Recipe object
        return new Recipe(components[0], components[1], components[2], components[3], components[4]);
    }
    public String getImage(){
        return this.image;
    }
     // Getters and setters for each attribute
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public String getMealType() {
        return this.mealType;
    }

    public void setMealType(String type) {
        this.mealType = type;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String text) {
        this.ingredients = text;
    }

    public String getSteps() {
        return this.steps;
    }
    
    public void setSteps(String text) {
        this.steps = text;
    }

     // Method to check if two Recipe objects are equal
    public boolean equals(Recipe other) {
        // Check if all attributes of this recipe match those of the other recipe
        return this.ingredients.equals(other.getIngredients()) && this.title.equals(other.getTitle())
                && this.steps.equals(other.getSteps()) && (this.mealType == other.getMealType());
    }

    // Method to return a string representation of the Recipe
    public String toString() {
        // Format and return the recipe details as a semicolon-separated string
        return String.format("%s;%s;%s;%s;%s", this.title, this.mealType, this.ingredients, this.steps, this.datetime.toString());
    }

}

