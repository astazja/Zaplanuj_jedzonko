package pl.coderslab.model;

public class RecipePlan {
    private Integer id;
    private Integer recipeId;
    private String mealName;
    private Integer displayOrder;
    private  Integer planId;

    public RecipePlan() {
    }
    public RecipePlan (Integer id, Integer recipeId, String mealName, Integer displayOrder, Integer planId) {
        this.id = id;
        this.recipeId = recipeId;
        this.mealName = mealName;
        this.displayOrder = displayOrder;
        this.planId = planId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
