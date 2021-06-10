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
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());

        request.setAttribute("planId",request.getParameter("planId"));
        request.setAttribute("recipeId",request.getParameter("recipeId"));

        getServletContext().getRequestDispatcher("/deleteRecipeFromPlan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String delete = request.getParameter("delete");
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        int planId = Integer.parseInt(request.getParameter("planId"));
        if (delete.equals("ok")) {

            RecipePlanDao recipePlanDao = new RecipePlanDao();
            recipePlanDao.deleteRecipePlan(planId, recipeId);
        }
        response.sendRedirect("/app/plan/details?id="+planId);

    }
}
