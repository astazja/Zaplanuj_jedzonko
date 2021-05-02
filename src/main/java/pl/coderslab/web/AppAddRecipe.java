package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AppAddRecipe", value = "/app/recipe/add")
public class AppAddRecipe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());
        getServletContext().getRequestDispatcher("/app-add-recipe.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userId") != null) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String preparationTime = request.getParameter("time");
            String preparation = request.getParameter("preparation");
            String ingredients = request.getParameter("ingredients");
            List<String> ingredientsList = Arrays.asList(ingredients.split(", "));

            if(name.isEmpty() || description.isEmpty() || preparationTime.isEmpty() || preparation.isEmpty() || ingredients.isEmpty()) {
                request.setAttribute("info", "Nie wypełniono wszystkich pól.");
                getServletContext().getRequestDispatcher("/app-add-recipe.jsp").forward(request, response);
            }else {
                Recipe recipe = new Recipe();
                recipe.setName(name);
                recipe.setIngredients(ingredientsList);
                recipe.setDescription(description);
                recipe.setCreated(LocalDateTime.now());
                recipe.setUpdated(LocalDateTime.now());
                recipe.setPreparationTime(Integer.parseInt(preparationTime));
                recipe.setPreparation(preparation);
                recipe.setAdminId((int) session.getAttribute("userId"));

                RecipeDao recipeDao = new RecipeDao();
                recipeDao.createRecipe(recipe);

                response.sendRedirect(request.getContextPath() + "/app/recipe/list");
            }
        }
    }
}
