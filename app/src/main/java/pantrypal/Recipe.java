package pantrypal;
public class Recipe {
    String title;
    String mealType;
    String ingredients;
    String steps;

    Recipe (String title, String mealType, String ingredients, String steps) {
        this.title = title;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // returns Recipe based on string representation with format title;mealType;ingredients;steps
    public static Recipe of (String representation) {
        System.out.println(representation);
        String[] components = representation.split(";");
        return new Recipe(components[0], components[1], components[2], components[3]);
    }

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
        this.title = ingredients;
    }

    public String getSteps() {
        return this.steps;
    }
    
    public void setSteps(String text) {
        this.steps = text;
    }

    public boolean equals(Recipe other) {
        return this.ingredients.equals(other.getIngredients()) && this.title.equals(other.getTitle())
                && this.steps.equals(other.getSteps()) && (this.mealType == other.getMealType());
    }


}

