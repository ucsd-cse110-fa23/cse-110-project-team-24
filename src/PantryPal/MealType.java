package PantryPal;

public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        switch (this) {
            case BREAKFAST:
                return "breakfast";
            case LUNCH:
                return "lunch";
            case DINNER:
                return "dinner";
            default:
                return null;
        }
    }
}
