package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipe", value = "/app/recipe/delete")
public class DeleteRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        RecipeDao recipeDao = new RecipeDao();
        RecipePlanDao recipePlanDao = new RecipePlanDao();

        if(recipePlanDao.isRecipeAdded(id) == false) {
            request.setAttribute("isRecipeAdded", false);
        }else {
            request.setAttribute("isRecipeAdded", true);
        }

        Recipe recipe = recipeDao.readRecipe(id);
        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("/deleteRecipe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        RecipeDao recipeDao = new RecipeDao();
        RecipePlanDao recipePlanDao = new RecipePlanDao();
        if(recipePlanDao.isRecipeAdded(id) == false) {
            recipeDao.deleteRecipe(id);
        }
        response.sendRedirect(request.getContextPath() + "/app/recipe/list");
    }
}
