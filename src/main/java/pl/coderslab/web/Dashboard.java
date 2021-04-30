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
//        HttpSession sess = request.getSession();
//        Admin admin = (Admin) sess.getAttribute("admin");
        Admin admin = new Admin();
        admin.setId(1);
        RecipeDao recipeDao = new RecipeDao();
        PlanDao planDao = new PlanDao();

        if (admin == null) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("countRecipes", recipeDao.getNumberOfRecipe(admin.getId()));
            request.setAttribute("countPlans", planDao.getNumberOfPlan(admin.getId()));
            request.setAttribute("lastPlan", planDao.readLastAdded());

            Plan plan = planDao.read(admin.getId());
            Map<String, List<RecipePlanDetails>> recipePlanDetailsList = planDao.readPlanDetails(admin.getId());


            request.setAttribute("plan", plan);
            request.setAttribute("recipePlanList", recipePlanDetailsList);

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}

