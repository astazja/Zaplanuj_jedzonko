package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppPlanDetails", value = "/app/plan/details")
public class AppPlanDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        List<RecipePlan> recipePlanList = planDao.readPlanDetails(id);
        request.setAttribute("plan", plan);
        request.setAttribute("recipePlanList", recipePlanList);
        request.getRequestDispatcher("/app-plan-details.jsp").forward(request, response);
    }
}
