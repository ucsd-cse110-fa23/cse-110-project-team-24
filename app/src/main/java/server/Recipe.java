package server;

import org.bson.Document;

public class Recipe {
    public static final String BREAKFAST = "Breakfast";
    public static final String LUNCH = "Lunch";
    public static final String DINNER = "Dinner";

    String title;
    String mealType;
    String ingredients;
    String steps;
    String Date;

    Recipe (String title, String mealType, String ingredients, String steps, String Date) {
        this.title = title;
        this.mealType = getMealType(mealType);
        this.ingredients = ingredients;
        this.steps = steps;
        this.Date = Date;
    }

    /**
     * Decides which meal type parameter is most likely to be. Defaults to "Dinner".
     * 
     * @param mealType: String indicating mealType
     * @return one of three possible meal types based on mealType parameter
     */
    static String getMealType(String mealType) {
        if (isBreakfast(mealType))
            return BREAKFAST;
        if (isLunch(mealType))
            return LUNCH;
        return DINNER;
    }

    // return true if meal type is most likely Lunch
    private static boolean isLunch(String mealType) {
        if (mealType == null)
            return false;
        return mealType.contains("un") || mealType.contains("ch")
                || mealType.contains("UN") || mealType.contains("CH");
    }

    // return true if meal type is most likely breakfast
    private static boolean isBreakfast(String mealType) {
        if (mealType == null)
            return false;
        return mealType.contains("eak") || mealType.contains("ast") 
                || mealType.contains("br") || mealType.contains("est")
                || mealType.contains("EAK") || mealType.contains("AST") 
                || mealType.contains("BR") || mealType.contains("EST");
    }

    public String getDate(){
        return this.Date;
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
        this.ingredients = text;
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

    // return string representation of recipe
    public String toString() {
        return String.format("%s;%s;%s;%s;%s", this.title, this.mealType, this.ingredients, this.steps, this.Date);
    }
    public Document toDocument(){
        Document Recipe = new Document().append("RecipeName", this.title)
        .append("MealType", this.mealType)
        .append("Ingredient List", this.ingredients)
        .append("Steps", this.steps)
        .append("Date", this.Date);
        return Recipe;
    }
}

