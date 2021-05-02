package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeletePlan", value = "/app/plan/delete")
public class DeletePlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());

        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("plan", plan);
        request.getRequestDispatcher("/deletePlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        planDao.delete(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/app/plan/list");
    }
}
