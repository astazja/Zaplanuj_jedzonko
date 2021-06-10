package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "Recipes", value = "/recipes")
public class Recipes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes;
        if (search == null) {
            recipes = recipeDao.findAllRecipes();
        } else {
            recipes = recipeDao.findRecipes(search);
        }

        if (recipes != null){
            Collections.reverse(recipes);
            request.setAttribute("recipes", recipes);
        }

        getServletContext().getRequestDispatcher("/recipes.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
