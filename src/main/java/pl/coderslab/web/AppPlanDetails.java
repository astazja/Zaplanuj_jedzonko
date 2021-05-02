package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AppPlanDetails", value = "/app/plan/details")
public class AppPlanDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());

        int id = Integer.parseInt(request.getParameter("id"));
        PlanDao planDao = new PlanDao();

        Plan plan = planDao.read(id);
        Map<String , List<RecipePlanDetails>> recipePlanDetailsList = planDao.readPlanDetails(id);


        request.setAttribute("plan", plan);
        request.setAttribute("recipePlanList", recipePlanDetailsList);

        request.getRequestDispatcher("/app-plan-details.jsp").forward(request, response);
    }
}
