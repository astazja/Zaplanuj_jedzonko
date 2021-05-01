package pl.coderslab.web;

import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.RecipePlan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeFromPlan", value = "/app/recipe/plan/delete")
public class DeleteRecipeFromPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipePlanDao recipePlanDao = new RecipePlanDao();
        RecipePlan recipePlan = recipePlanDao.readRecipePlan(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("recipePlan", recipePlan);
        request.getRequestDispatcher("/deleteRecipeFromPlan.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RecipePlanDao recipePlanDao = new RecipePlanDao();
    recipePlanDao.deleteRecipePlan(Integer.parseInt(request.getParameter("id")));
    response.sendRedirect(request.getContextPath()+"/app/plan/details");

    }
}
