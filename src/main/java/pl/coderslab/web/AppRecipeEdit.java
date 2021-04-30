package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AppRecipeEdit", value = "/app/recipe/edit")
public class AppRecipeEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.readRecipe(Integer.parseInt(request.getParameter("id")));
        List<String> ingredients = recipe.getIngredients();
        StringBuilder onLineIngredients = new StringBuilder();
        for (String ingredient : ingredients) {
            if(onLineIngredients.length() > 0) {
                onLineIngredients.append(", ");
            }
            onLineIngredients.append(ingredient);
        }
        request.setAttribute("recipe", recipe);
        request.setAttribute("ingredients", onLineIngredients);
        getServletContext().getRequestDispatcher("/app-recipe-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String preparationTime = request.getParameter("time");
            String preparation = request.getParameter("preparation");
            String ingredients = request.getParameter("ingredients");
            List<String> ingredientsList = Arrays.asList(ingredients.split(", "));

            if(name.isEmpty() || description.isEmpty() || preparationTime.isEmpty() || preparation.isEmpty() || ingredients.isEmpty()) {
                request.setAttribute("info", "Nie wypełniono wszystkich pól.");
                response.sendRedirect("/app/recipe/edit?id=" + id);
            }else {
                RecipeDao recipeDao = new RecipeDao();
                Recipe recipe = recipeDao.readRecipe(id);
                recipe.setName(name);
                recipe.setIngredients(ingredientsList);
                recipe.setDescription(description);
                recipe.setUpdated(LocalDateTime.now());
                recipe.setPreparationTime(Integer.parseInt(preparationTime));
                recipe.setPreparation(preparation);
                recipe.setAdminId((int) session.getAttribute("userId"));

                recipeDao.updateRecipe(recipe);
                response.sendRedirect(request.getContextPath() + "/app/recipe/list");
            }
        }
    }
}
