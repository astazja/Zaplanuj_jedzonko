package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlanDetails;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanDao {
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE plan SET name = ?, description = ?, created = ?, admin_id = ? WHERE id = ?;";
    private static final String READ_LAST_ADDED_PLAN = "SELECT * FROM plan  WHERE id = (SELECT MAX(id) FROM plan)";
    private static final String NUMBER_OF_ADDED_PLAN = "SELECT COUNT(*) FROM plan WHERE admin_id = ?;";
    private static final String READ_PLAN_DETAILS_QUERY = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description, recipe.id AS recipe_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";


    public Plan readLastAdded() {
        Plan lastPlan = new Plan();
        try (Connection connection = DbUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(READ_LAST_ADDED_PLAN);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lastPlan.setId(resultSet.getInt("id"));
                    lastPlan.setName(resultSet.getString("name"));
                    lastPlan.setDescription(resultSet.getString("description"));
                    lastPlan.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
                    lastPlan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastPlan;
    }

    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setTimestamp(3, Timestamp.valueOf(plan.getCreated()));
            insertStm.setInt(4, plan.getAdminId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(plan.getCreated()));
            statement.setInt(4, plan.getAdminId());
            statement.setInt(5, plan.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    public Integer getNumberOfPlan(Integer adminId) {
        try(Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(NUMBER_OF_ADDED_PLAN);

            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String, List<RecipePlanDetails>> readPlanDetails(Integer planId) {
        Map<String , List<RecipePlanDetails>> planDetailsMap = new HashMap<>();

        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_PLAN_DETAILS_QUERY);
            statement.setInt(1, planId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String dayName = resultSet.getString("day_name");

                List<RecipePlanDetails> recipePlanDetailsList = new ArrayList<>();

                RecipePlanDetails recipePlanDetails = new RecipePlanDetails();
                recipePlanDetails.setDayName(resultSet.getString("day_name"));
                recipePlanDetails.setMealName(resultSet.getString("meal_name"));
                recipePlanDetails.setRecipeName(resultSet.getString("recipe_name"));
                recipePlanDetails.setRecipeDescription(resultSet.getString("recipe_description"));
                recipePlanDetails.setRecipeId(resultSet.getInt("recipe_id"));
                recipePlanDetailsList.add(recipePlanDetails);
                if(planDetailsMap.get(dayName) == null) {
                    planDetailsMap.put(dayName, recipePlanDetailsList);
                }else {
                    planDetailsMap.get(dayName).addAll(recipePlanDetailsList);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return planDetailsMap;
    }
}
