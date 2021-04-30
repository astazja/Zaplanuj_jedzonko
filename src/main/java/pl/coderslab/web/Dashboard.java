package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sess = request.getSession();

        if (sess.getAttribute("userId") == null) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            RecipeDao recipeDao = new RecipeDao();
            PlanDao planDao = new PlanDao();

            request.setAttribute("countRecipes", recipeDao.getNumberOfRecipe());
            request.setAttribute("countPlans", planDao.getNumberOfPlan());
            request.setAttribute("lastPlan", planDao.readLastAdded());

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}

