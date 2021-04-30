package pl.coderslab.dao;

import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipePlanDao {
    private static final String CHECK_RECIPE_IS_ADDED_TO_PLANS_QUERY = "SELECT COUNT(*) as count FROM recipe_plan WHERE recipe_id = ?;";

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
