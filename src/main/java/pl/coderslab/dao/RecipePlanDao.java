package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipePlanDao {
    private static final String CREATE_RECIPE_PLAN = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?,?,?)";
    private static final String CHECK_RECIPE_IS_ADDED_TO_PLANS_QUERY = "SELECT COUNT(*) as count FROM recipe_plan WHERE recipe_id = ?;";

    public RecipePlan create(RecipePlan recipePlan) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_RECIPE_PLAN, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, recipePlan.getRecipeId());
            statement.setString(2, recipePlan.getMealName());
            statement.setInt(3, recipePlan.getDisplayOrder());
            statement.setInt(4, recipePlan.getDayNameId());
            statement.setInt(5, recipePlan.getPlanId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                recipePlan.setId(resultSet.getInt(1));
            }
            return recipePlan;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
  
public boolean isRecipeAdded(Integer recipeId) {
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(CHECK_RECIPE_IS_ADDED_TO_PLANS_QUERY);
            statement.setInt(1, recipeId);
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            if(count > 0) {
                return true;
            }
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}