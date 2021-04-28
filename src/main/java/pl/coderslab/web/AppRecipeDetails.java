package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppRecipeDetails", value = "/app/recipe/details")
public class AppRecipeDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.readRecipe(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("/app-recipe-details.jsp").forward(request,response);
    }
}
