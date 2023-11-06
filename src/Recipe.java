public class Recipe {
    String title;
    MealType mealType;
    String ingredients;
    String steps;

    Recipe (String title, MealType mealType, String ingredients, String steps) {
        this.title = title;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public MealType getMealType() {
        return this.mealType;
    }

    public void setMealType(MealType type) {
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
