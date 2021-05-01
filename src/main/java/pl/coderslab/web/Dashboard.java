package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlanDetails;

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

        if (sess.getAttribute("userData") == null) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            RecipeDao recipeDao = new RecipeDao();
            PlanDao planDao = new PlanDao();

            Admin admin = (Admin) sess.getAttribute("userData");

            Plan plan = planDao.readLastAdded();
            Map<String , List<RecipePlanDetails>> recipePlanDetailsList = planDao.readPlanDetails(plan.getId());

            request.setAttribute("recipePlanList", recipePlanDetailsList);

            request.setAttribute("countRecipes", recipeDao.getNumberOfRecipe());
            request.setAttribute("countPlans", planDao.getNumberOfPlan());
            request.setAttribute("lastPlan", plan);
            request.setAttribute("firstName", admin.getFirstName());

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}

