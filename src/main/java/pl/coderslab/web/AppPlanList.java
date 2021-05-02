package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppPlanList", value = "/app/plan/list")
public class AppPlanList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());

        PlanDao planDao = new PlanDao();
        List<Plan> allPlanList = planDao.findAll();
        allPlanList.sort((plan1, plan2) -> plan2.getCreated().compareTo(plan1.getCreated()));
        request.setAttribute("plans", allPlanList);
        getServletContext().getRequestDispatcher("/app-plan-list.jsp").forward(request, response);
    }
}
