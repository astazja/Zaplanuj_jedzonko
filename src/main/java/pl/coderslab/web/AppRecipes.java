package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AppRecipes", value = "/app/recipe/list")
public class AppRecipes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        request.setAttribute("recipes", recipeDao.findAllRecipes());
        getServletContext().getRequestDispatcher("/app-recipes.jsp").forward(request, response);
    }
}
