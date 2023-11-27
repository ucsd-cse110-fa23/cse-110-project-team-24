package pantrypal;


public class Recipe {
     // Class attributes for recipe details
    String title;
    String mealType;
    String ingredients;
    String steps;

    // Constructor to create a new Recipe instance
    public Recipe (String title, String mealType, String ingredients, String steps) {
        this.title = title;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(server.Recipe avocadoSalad) {
    }

    // Static method to create a Recipe instance from a string representation
    // returns Recipe based on string representation with format title;mealType;ingredients;steps
    public static Recipe of (String representation) {
        // Split the string representation into its components
        String[] components = representation.split(";");
        // Create and return a new Recipe object
        return new Recipe(components[0], components[1], components[2], components[3]);
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
        return String.format("%s;%s;%s;%s", this.title, this.mealType, this.ingredients, this.steps);
    }

}

