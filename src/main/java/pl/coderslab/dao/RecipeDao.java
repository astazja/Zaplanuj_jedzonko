package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDao {

    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_RECIPE_QUERY = "SELECT * FROM recipe WHERE id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, ingredients = ?, description = ?, created = ?, updated = ?, preparation_time = ?, preparation = ?, admin_id = ? WHERE id = ?;";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe;";

    public Recipe createRecipe(Recipe recipe) {
        try(Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE_QUERY, Statement.RETURN_GENERATED_KEYS);
            sendToDatabase(recipe, statement);
            int result = statement.executeUpdate();
            if(result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generetedKeys = statement.getGeneratedKeys()) {
                if(generetedKeys.first()) {
                    recipe.setId(generetedKeys.getInt(1));
                    return recipe;
                }else {
                    throw new RuntimeException("Genereted Key was not found");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Recipe readRecipe(int recipeId) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY);
            statement.setInt(1, recipeId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getFromDatabase(resultSet, recipe);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }
    public void updateRecipe(Recipe recipe) {
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY);
            statement.setInt(9, recipe.getId());
            sendToDatabase(recipe, statement);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void sendToDatabase(Recipe recipe, PreparedStatement statement) throws SQLException {
        statement.setString(1, recipe.getName());
        statement.setString(2, String.join(", ", recipe.getIngredients()));
        statement.setString(3, recipe.getDescription());
        statement.setTimestamp(4, Timestamp.valueOf(recipe.getCreated()));
        statement.setTimestamp(5, Timestamp.valueOf(recipe.getUpdated()));
        statement.setInt(6, recipe.getPreparationTime());
        statement.setString(7, recipe.getPreparation());
        statement.setInt(8, recipe.getAdminId());
    }

    public void deleteRecipe(int recipeId) {
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY);
            statement.setInt(1, recipeId);
            statement.executeUpdate();
            boolean deleted = statement.execute();
            if(!deleted) {
                throw new NotFoundException("Recipe not found");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Recipe> findAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                getFromDatabase(resultSet, recipeToAdd);
                recipeList.add(recipeToAdd);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    private void getFromDatabase(ResultSet resultSet, Recipe recipe) throws SQLException {
        recipe.setId(resultSet.getInt("id"));
        recipe.setName(resultSet.getString("name"));
        recipe.setIngredients(Arrays.asList(resultSet.getString("ingredients").split(", ")));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        recipe.setUpdated(resultSet.getTimestamp("updated").toLocalDateTime());
        recipe.setPreparationTime(resultSet.getInt("preparation_time"));
        recipe.setPreparation(resultSet.getString("preparation"));
        recipe.setAdminId(resultSet.getInt("admin_id"));
    }
}
