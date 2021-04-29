package pl.coderslab.model;

public class RecipePlanDetails {
    private String dayName;
    private String mealName;
    private String recipeName;
    private String recipeDescription;
    private Integer recipeId;

    public RecipePlanDetails(){
    }

    public RecipePlanDetails(String dayName, String mealName, String recipeName, String recipeDescription, Integer recipeId) {
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeId = recipeId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Integer getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "RecipePlanDetails{" +
                "dayName='" + dayName + '\'' +
                ", mealName='" + mealName + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
